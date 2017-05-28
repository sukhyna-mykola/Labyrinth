package devchallenge.labyrinth.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import devchallenge.labyrinth.models.direction.Direction;


public class Ball extends Cell {

    private float x, y;

    private Direction direction;

    public Ball(int row, int column, int width, int height) {
        super(row, column, width, height);

        this.x = column * this.width;
        this.y = row * this.height;

        this.direction = Direction.NONE;

        this.color = Color.RED;
    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        p.setColor(color);
        float cx = x + width / 2;
        float cy = y + height/ 2;
        float r = height / 2 ;
        canvas.drawCircle(cx, cy, r, p);
    }

    public Direction getDirection() {
        return direction;
    }


    public void moveY(float dy) {


        x = column * width;
        y += dy;

        row = (int) ((y + height / 2) / height);

        if (Math.abs(row * height - y) < dy)
            y = row * height;

    }

    public void moveX(float dx) {

        x += dx;
        y = row * height;

        column = (int) ((x + width / 2) / width);

        if (Math.abs(column * width - x) < dx)
            x = column * width;

    }

    public void setDirection(Direction direction) {
        this.direction = direction;
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

    public void setY(float y) {
        this.y = y;
    }
}
