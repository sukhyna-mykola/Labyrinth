package devchallenge.labyrinth.models;

import android.graphics.Canvas;
import android.graphics.Paint;


public class LabelCell extends Cell {
    public static final String LABEL_CELL_START = "LABEL_CELL_START";
    public static final String LABEL_CELL_FINISH = "LABEL_CELL_FINISH";

    public LabelCell(int row, int column, int width, int height, int color, String type) {
        super(row, column, width, height);
        this.color = color;
        this.type = type;
    }


    @Override
    public void draw(Canvas canvas, Paint p) {
        p.setColor(color);
        canvas.drawRect(column * width, row * height, (column + 1) * width, (row + 1) * height, p);
    }
}
