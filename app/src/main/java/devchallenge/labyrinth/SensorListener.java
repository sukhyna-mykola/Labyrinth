package devchallenge.labyrinth;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import devchallenge.labyrinth.callbacks.GameCallbacks;
import devchallenge.labyrinth.models.direction.DirectionsEnum;

public class SensorListener implements SensorEventListener {

    public static final float e = (float) Math.PI / (2 * 9); //10

    private GameCallbacks callbacks;

    private float[] valuesAccel = new float[3];
    private float[] valuesMagnet = new float[3];
    private float[] valuesResult = new float[3];
    private float[] r = new float[9];

    public SensorListener(GameCallbacks callbacks) {
        this.callbacks = callbacks;
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                for (int i = 0; i < 3; i++) {
                    valuesAccel[i] = event.values[i];
                }
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                for (int i = 0; i < 3; i++) {
                    valuesMagnet[i] = event.values[i];
                }
                break;
        }
        getDeviceOrientation();
    }

    private void getDeviceOrientation() {
        SensorManager.getRotationMatrix(r, null, valuesAccel, valuesMagnet);
        SensorManager.getOrientation(r, valuesResult);

        if (Math.abs(valuesResult[1]) > e || Math.abs(valuesResult[2]) > e) {
            if (Math.abs(valuesResult[1]) < Math.abs(valuesResult[2])) {
                if (valuesResult[2] > 0) {
                    callbacks.changeDirection(DirectionsEnum.RIGHT);
                } else {
                    callbacks.changeDirection(DirectionsEnum.LEFT);
                }
            } else {
                if (valuesResult[1] > 0) {
                    callbacks.changeDirection(DirectionsEnum.UP);
                } else {
                    callbacks.changeDirection(DirectionsEnum.DOWN);
                }
            }
        } else {
            callbacks.changeDirection(DirectionsEnum.NONE);
        }

    }

}
