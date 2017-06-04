package devchallenge.labyrinth.models.direction;


import devchallenge.labyrinth.models.Ball;

import static devchallenge.labyrinth.models.Ball.MAX_V;


public class UpDirection extends DirectionState {
    @Override
    public void move(Ball ball) {
        ball.vy -= ball.dv;
        if (ball.vy < -MAX_V) {
            ball.vy = -MAX_V;
        }

        ball.setY(ball.getY() + ball.vy);

        ball.setRow((int) ((ball.getY() + ball.getHeight()) / ball.getHeight()));
    }

    public UpDirection(Ball ball) {
        this.direction = DirectionsEnum.UP;
        ball.setRow((int) ((ball.getY() + ball.getHeight()) / ball.getHeight()));
        ball.setX(ball.getColumn() * ball.getWidth());
    }
}
