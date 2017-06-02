package devchallenge.labyrinth;


import devchallenge.labyrinth.models.direction.DirectionsEnum;

public interface SensorCallbacks {

    void registerSensors();

    void unregisterSensors();
}
