package devchallenge.labyrinth.callbacks;


import devchallenge.labyrinth.callbacks.LoaderCallbacks;
import devchallenge.labyrinth.models.direction.DirectionsEnum;

public interface GameCallbacks extends LoaderCallbacks {
    void endGame();

    void pauseGame();

    void startGame();

    void update();

    void newGame();

    void exitGame();

    void changeDirection(DirectionsEnum direction);

    void showSolution();

    void hideSolution();

}
