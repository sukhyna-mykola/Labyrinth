package devchallenge.labyrinth.models.direction;


import devchallenge.labyrinth.models.Ball;

public abstract class DirectionState {
    protected DirectionsEnum direction;


    public DirectionsEnum getDirection() {
        return direction;
    }

    public void setDirection(DirectionsEnum direction) {
        this.direction = direction;
    }

    public abstract void move(Ball ball);

}
