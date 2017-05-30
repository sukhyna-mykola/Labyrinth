package devchallenge.labyrinth.models.direction;


import devchallenge.labyrinth.models.Ball;

import static devchallenge.labyrinth.models.Ball.MAX_V;


public class LeftDirection extends DirectionState {
    @Override
    public void move(Ball ball) {
        ball.vx -= ball.a;
        if (ball.vx < -MAX_V) {
            ball.vx = -MAX_V;
        }


        ball.setX(ball.getX() + ball.vx);


        ball.setColumn((int) ((ball.getX() + ball.getWidth()) / ball.getWidth()));
    }

    public LeftDirection(Ball ball) {
        this.direction = DirectionsEnum.LEFT;
        ball.setColumn((int) ((ball.getX() + ball.getWidth()) / ball.getWidth()));
        ball.setY(ball.getRow() * ball.getHeight());
    }
}
