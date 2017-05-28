package devchallenge.labyrinth;

import android.graphics.Color;

import devchallenge.labyrinth.models.Ball;
import devchallenge.labyrinth.models.Border;
import devchallenge.labyrinth.models.Cell;
import devchallenge.labyrinth.models.LabelCell;

public class Game implements UpdateCallback {

    private LabelCell endCell, startCell;
    private Ball ball;
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
        ball = new Ball(rowCount - 2, columnCount - 2, size, size);
        endCell = new LabelCell(rowCount - 2, columnCount - 1, size, size, Color.GREEN);
        startCell = new LabelCell(0, 1, size, size, Color.YELLOW);
        // ball = new Ball(startCell.getRow(), startCell.getColumn(), size, size);
        labyrinth[ball.getRow()][ball.getColumn()] = ball;
        labyrinth[endCell.getRow()][endCell.getColumn()] = endCell;
        labyrinth[startCell.getRow()][startCell.getColumn()] = startCell;
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
        int row = ball.getRow();
        int column = ball.getColumn();

        switch (ball.getDirection()) {
            case DOWN:
                if (row < rowCount - 1)
                    if (!(labyrinth[row + 1][column] instanceof Border) || ball.getY() + ball.getHeight() < (row + 1) * ball.getHeight())
                        ball.moveY(0.25f);
                break;
            case UP:
                if (row > 0)
                    if (!(labyrinth[row - 1][column] instanceof Border) || ball.getY() > row * ball.getHeight())
                        ball.moveY(-0.25f);
                break;
            case RIGHT:
                if (column < columnCount - 1)
                    if (!(labyrinth[row][column + 1] instanceof Border) || ball.getX() + ball.getWidth() < (column + 1) * ball.getWidth())
                        ball.moveX(0.25f);
                break;
            case LEFT:
                if (column > 0)
                    if (!(labyrinth[row][column - 1] instanceof Border) || ball.getX() > column * ball.getWidth())
                        ball.moveX(-0.25f);
                break;
        }
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

}
