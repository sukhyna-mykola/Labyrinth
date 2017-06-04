package devchallenge.labyrinth.models.direction;


import devchallenge.labyrinth.models.Ball;


public class NoneDirection extends DirectionState {
    @Override
    public void move(Ball ball) {
//------------------
    }


    public NoneDirection(Ball ball) {
        this.direction = DirectionsEnum.NONE;
        ball.setColumn((int) ((ball.getX() + ball.getWidth() / 2) / ball.getWidth()));
        ball.setRow((int) ((ball.getY() + ball.getHeight() / 2) / ball.getHeight()));

    }
}
