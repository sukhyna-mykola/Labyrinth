package devchallenge.labyrinth;


import devchallenge.labyrinth.models.direction.DirectionsEnum;

public interface GameCallbacks {
    void endGame();

    void pauseGame();

    void startGame();

    void loadGame();

    void saveGame();

    void update();

    void newGame();

    void exitGame();

    void changeDirection(DirectionsEnum direction);

    void registerSensors();

    void unregisterSensors();
}
