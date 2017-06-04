package devchallenge.labyrinth.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Border extends Cell {
    public static final String BORDER_CELL = "BORDER_CELL";

    public Border(int row, int column, int width, int height) {
        super(row, column, width, height);
        this.color = Color.BLUE;
        this.type = BORDER_CELL;
    }


    @Override
    public void draw(Canvas canvas, Paint p) {
        p.setColor(color);
        canvas.drawRect(column * width, row * height, (column + 1) * width, (row + 1) * height, p);
    }
}
