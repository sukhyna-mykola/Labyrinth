package devchallenge.labyrinth;

import android.graphics.Color;

import devchallenge.labyrinth.models.Ball;
import devchallenge.labyrinth.models.Border;
import devchallenge.labyrinth.models.Cell;
import devchallenge.labyrinth.models.LabelCell;
import devchallenge.labyrinth.models.direction.DirectionState;
import devchallenge.labyrinth.models.direction.DirectionsEnum;
import devchallenge.labyrinth.models.direction.NoneDirection;

public class Game implements GameCallbacks {

    private static final String TAG = "GAME";
    private LabelCell endCell, startCell;
    private Ball ball;

    private DirectionState directionState;
    private LabyrinthGenerator labyrinthGenerator;
    private int rowCount;
    private int columnCount;
    private int size;
    private GameCallbacks gameCallbacks;

    public Game(int rowCount, int columnCount, GameCallbacks gameCallbacks) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.gameCallbacks = gameCallbacks;
    }

    public void init(int size) {
        this.size = size;
        labyrinthGenerator = new LabyrinthGenerator(rowCount, columnCount, size, size);
        labyrinth = labyrinthGenerator.generateMaze();
        //  ball = new Ball(rowCount - 2, columnCount - 2, size, size);
        endCell = new LabelCell(rowCount - 2, columnCount - 1, size, size, Color.GREEN);
        startCell = new LabelCell(0, 1, size, size, Color.WHITE);
        ball = new Ball(startCell.getRow(), startCell.getColumn(), size, size);
        labyrinth[ball.getRow()][ball.getColumn()] = ball;
        labyrinth[endCell.getRow()][endCell.getColumn()] = endCell;
        labyrinth[startCell.getRow()][startCell.getColumn()] = startCell;
        directionState = new NoneDirection(ball);

        gameCallbacks.registerSensors();
    }

    public int getSize() {
        return size;
    }

    private Cell labyrinth[][];

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

    public LabyrinthGenerator getLabyrinthGenerator() {
        return labyrinthGenerator;

    }


    @Override
    public void update() {

        move();

        if (ball.getColumn() == endCell.getColumn() && ball.getRow() == endCell.getRow()) {
            gameCallbacks.endGame();
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
        labyrinth = labyrinthGenerator.generateMaze();
        ball.setColumn(startCell.getColumn());
        ball.setRow(startCell.getRow());
        ball.setX(startCell.getColumn() * ball.getWidth());
        ball.setY(startCell.getRow() * ball.getHeight());
        labyrinth[endCell.getRow()][endCell.getColumn()] = endCell;
        labyrinth[startCell.getRow()][startCell.getColumn()] = startCell;
        directionState = new NoneDirection(ball);
        gameCallbacks.startGame();
    }

    @Override
    public void exitGame() {

    }

    private void move() {
        int row = ball.getRow();
        int column = ball.getColumn();

        switch (directionState.getDirection()) {
            case DOWN:
                if (row < rowCount - 1)
                    if (!(labyrinth[row + 1][column] instanceof Border))
                        directionState.move(ball);
                //directionState.directionState(ball);
                break;
            case UP:
                if (row > 0)
                    if (!(labyrinth[row - 1][column] instanceof Border))
                        directionState.move(ball);
                //directionState.directionState(ball);
                break;
            case RIGHT:
                if (column < columnCount - 1)
                    if (!(labyrinth[row][column + 1] instanceof Border))
                        directionState.move(ball);
                // directionState.directionState(ball);
                break;
            case LEFT:
                if (column > 0)
                    if (!(labyrinth[row][column - 1] instanceof Border))
                        directionState.move(ball);

                //directionState.directionState(ball);
                break;
            case NONE:
                directionState.move(ball);
                break;
        }
    }

    public DirectionsEnum getDirection() {
        return directionState.getDirection();
    }

    public void changeDirection(DirectionState directionState) {
        this.directionState = directionState;
        this.ball.resetV();
    }

    @Override
    public void endGame() {

    }

    @Override
    public void pauseGame() {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void loadGame() {

    }

    @Override
    public void saveGame() {

    }

    @Override
    public void changeDirection(DirectionsEnum direction) {

    }

    @Override
    public void registerSensors() {

    }

    @Override
    public void unregisterSensors() {

    }

  /*  public void changeV(float[] v){

        ball.vx = v[1];
        ball.vy = v[2];
        Log.d(TAG,"vx = "+ball.vx+", vy  = "+ball.vy);
    }*/

}
