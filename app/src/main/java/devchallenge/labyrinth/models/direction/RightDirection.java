package devchallenge.labyrinth.models.direction;


import devchallenge.labyrinth.models.Ball;

import static devchallenge.labyrinth.models.Ball.MAX_V;


public class RightDirection extends DirectionState {
    @Override
    public void move(Ball ball) {
        ball.vx += ball.a;
        if (ball.vx > MAX_V) {
            ball.vx = MAX_V;
        }

        ball.setX(ball.getX() + ball.vx);


        ball.setColumn((int) (ball.getX() / ball.getWidth()));
    }

    public RightDirection(Ball ball) {
        this.direction = DirectionsEnum.RIGHT;
        ball.setColumn((int) (ball.getX() / ball.getWidth()));
        ball.setY(ball.getRow() * ball.getHeight());
    }
}
