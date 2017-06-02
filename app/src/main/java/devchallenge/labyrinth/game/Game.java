package devchallenge.labyrinth.game;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import devchallenge.labyrinth.helpers.GameSaver;
import devchallenge.labyrinth.helpers.GameSettings;
import devchallenge.labyrinth.models.Ball;
import devchallenge.labyrinth.models.Border;
import devchallenge.labyrinth.models.Cell;
import devchallenge.labyrinth.models.LabelCell;
import devchallenge.labyrinth.models.direction.DirectionState;
import devchallenge.labyrinth.models.direction.DirectionsEnum;
import devchallenge.labyrinth.models.direction.DownDirection;
import devchallenge.labyrinth.models.direction.LeftDirection;
import devchallenge.labyrinth.models.direction.NoneDirection;
import devchallenge.labyrinth.models.direction.RightDirection;
import devchallenge.labyrinth.models.direction.UpDirection;

import static devchallenge.labyrinth.GameActivity.HEIGHT;
import static devchallenge.labyrinth.GameActivity.WIDTH;

public class Game implements GameCallbacks {

    private static final String TAG = "GAME";

    private LabelCell endCell, startCell;
    private Ball ball;

    private DirectionState directionState;
    private LabyrinthGenerator labyrinthGenerator;
    private int rowCount, columnCount;
    private int size;
    private GameCallbacks gameCallbacks;

    private GameSaver gameSaver;
    private Cell labyrinth[][];
    private GameSettings gameSettings;


    public Game(GameCallbacks gameCallbacks) {
        this.gameCallbacks = gameCallbacks;

        gameSettings = GameSettings.getInstance((Context) gameCallbacks);
        gameSaver = GameSaver.getInstance((Context) gameCallbacks);

    }

    @Override
    public void update() {

        move();

        if (ball.getColumn() == endCell.getColumn() && ball.getRow() == endCell.getRow()) {
            endGame();
        }
     /*   if (row != ball.getRow() || column != ball.getColumn()) {
            labyrinth[row][column] = null;
            row = ball.getRow();
            column = ball.getColumn();
            labyrinth[row][column] = ball;
        }*/
    }

    @Override
    public void newGame() {

        this.rowCount = gameSettings.getRowCount();
        this.columnCount = gameSettings.getColumnCount();

        calculateCellSize();

        labyrinthGenerator = new LabyrinthGenerator(rowCount, columnCount);

        labyrinth = labyrinthGenerator.generateMaze(size, size);


        if (gameSettings.getDefaultMode().equals(gameSettings.getMode())) {
            endCell = new LabelCell(rowCount - 2, columnCount - 1, size, size, Color.GREEN, LabelCell.LABEL_CELL_FINISH);
            startCell = new LabelCell(0, 1, size, size, Color.WHITE, LabelCell.LABEL_CELL_START);
        } else {
            calculateRandomPosition();
        }

        ball = new Ball(startCell.getRow(), startCell.getColumn(), size, size);

        labyrinth[endCell.getRow()][endCell.getColumn()] = endCell;
        labyrinth[startCell.getRow()][startCell.getColumn()] = startCell;

        directionState = new NoneDirection(ball);

        gameCallbacks.startGame();
    }
    
    @Override
    public void exitGame() {
        //------------
    }

    @Override
    public void endGame() {
        gameCallbacks.endGame();
    }

    @Override
    public void pauseGame() {
        //------------------
    }

    @Override
    public void startGame() {
        //-----------
    }

    @Override
    public void loadGame(String fileName) {
        try {
            gameSaver.load(fileName, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveGame(String fileName) {
        try {
            gameSaver.save(fileName, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeDirection(DirectionsEnum direction) {
        if (directionState.getDirection() != direction) {
            Log.d(TAG, "direction =  " + direction);
            switch (direction) {
                case DOWN:
                    directionState = new DownDirection(ball);
                    break;
                case UP:
                    directionState = new UpDirection(ball);
                    break;
                case LEFT:
                    directionState = new LeftDirection(ball);
                    break;
                case RIGHT:
                    directionState = new RightDirection(getBall());
                    break;
                case NONE:
                    directionState = new NoneDirection(getBall());
                    break;
            }
            ball.resetV();
        }

    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public void setLabyrinth(Cell[][] labyrinth) {
        this.labyrinth = labyrinth;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public void setEndCell(LabelCell endCell) {
        this.endCell = endCell;
    }

    public void setStartCell(LabelCell startCell) {
        this.startCell = startCell;
    }

    public int getSize() {
        return size;
    }

    public Ball getBall() {
        return ball;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Cell[][] getLabyrinth() {

        return labyrinth;
    }

    private void move() {
        int row = ball.getRow();
        int column = ball.getColumn();

        switch (directionState.getDirection()) {
            case DOWN:
                if (row < rowCount - 1)
                    if (!(labyrinth[row + 1][column] instanceof Border))
                        directionState.move(ball);
                break;
            case UP:
                if (row > 0)
                    if (!(labyrinth[row - 1][column] instanceof Border))
                        directionState.move(ball);
                break;
            case RIGHT:
                if (column < columnCount - 1)
                    if (!(labyrinth[row][column + 1] instanceof Border))
                        directionState.move(ball);
                break;
            case LEFT:
                if (column > 0)
                    if (!(labyrinth[row][column - 1] instanceof Border))
                        directionState.move(ball);

                break;
            case NONE:
                directionState.move(ball);
                break;
        }
    }

    private void calculateRandomPosition() {
        List<Point> points = searchEmptyElements();

        int pos = new Random().nextInt(points.size());
        Point p = points.get(pos);
        startCell = new LabelCell(p.x, p.y, size, size, Color.WHITE, LabelCell.LABEL_CELL_START);
        points.remove(p);

        pos = new Random().nextInt(points.size());
        p = points.get(pos);
        endCell = new LabelCell(p.x, p.y, size, size, Color.GREEN, LabelCell.LABEL_CELL_FINISH);


    }

    public void calculateCellSize() {
        this.size = Math.min(WIDTH / columnCount, HEIGHT / rowCount);
    }

    private List<Point> searchEmptyElements() {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (labyrinth[i][j] == null) {
                    points.add(new Point(i, j));
                }
            }
        }
        return points;
    }

}
