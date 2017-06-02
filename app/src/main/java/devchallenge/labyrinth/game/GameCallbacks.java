package devchallenge.labyrinth.game;


import devchallenge.labyrinth.LoaderCallbacks;
import devchallenge.labyrinth.models.direction.DirectionsEnum;

public interface GameCallbacks extends LoaderCallbacks {
    void endGame();

    void pauseGame();

    void startGame();

    void update();

    void newGame();

    void exitGame();

    void changeDirection(DirectionsEnum direction);

}
