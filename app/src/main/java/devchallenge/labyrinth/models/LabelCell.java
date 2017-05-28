package devchallenge.labyrinth.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by mykola on 28.05.17.
 */

public class LabelCell extends Cell {
    public LabelCell(int row, int column, int width, int height,int color) {
        super(row, column, width, height);
        this.color = color;
    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        p.setColor(color);
        canvas.drawRect(column * width, row * height, (column + 1) * width, (row + 1) * height, p);
    }
}
