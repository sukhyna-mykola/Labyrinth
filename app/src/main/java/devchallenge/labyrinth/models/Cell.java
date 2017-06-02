package devchallenge.labyrinth.models;


import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Cell {

    protected int color;
    protected int row, column;
    protected int width, height;
    protected String type;

    public String getType() {
        return type;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }


    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Cell(int row, int column) {

        this.row = row;
        this.column = column;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Cell(int row, int column, int width, int height) {

        this.row = row;
        this.column = column;
        this.width = width;
        this.height = height;
    }
    public abstract void draw(Canvas canvas, Paint p);
}
