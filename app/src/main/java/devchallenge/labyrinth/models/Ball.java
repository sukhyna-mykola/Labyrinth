package devchallenge.labyrinth.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Ball extends Cell {

    public float x, y;

    public float vx, vy, a = 0.015f;
    public static final float MAX_V = 1.5f;


    public Ball(int row, int column, int width, int height) {
        super(row, column, width, height);

        this.x = column * this.width;
        this.y = row * this.height;


        this.color = Color.RED;
    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        p.setColor(color);
        float cx = x + width / 2;
        float cy = y + height / 2;
        float r = height / 2;
        canvas.drawCircle(cx, cy, r, p);
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


    public void setX(float x) {

        this.x = x;
    }

    public void resetV() {
        vx = vy = 0;
    }

    public void setY(float y) {
        this.y = y;
    }
}
