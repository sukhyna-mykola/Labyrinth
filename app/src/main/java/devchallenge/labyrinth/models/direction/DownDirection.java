package devchallenge.labyrinth.models.direction;


import devchallenge.labyrinth.models.Ball;

import static devchallenge.labyrinth.models.Ball.MAX_V;


public class DownDirection extends DirectionState {
    @Override
    public void move(Ball ball) {

        ball.vy += ball.dv;

        if (ball.vy > MAX_V) {
            ball.vy = MAX_V;
        }

        ball.setY(ball.getY() + ball.vy);

        ball.setRow((int) (ball.getY() / ball.getHeight()));

    }

    public DownDirection(Ball ball) {
        this.direction = DirectionsEnum.DOWN;
        ball.setRow((int) (ball.getY() / ball.getHeight()));
        ball.setX(ball.getColumn() * ball.getWidth());
    }
}
