package devchallenge.labyrinth;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import devchallenge.labyrinth.callbacks.GameCallbacks;
import devchallenge.labyrinth.models.direction.DirectionsEnum;

public class SensorListener implements SensorEventListener {
    private GameCallbacks callbacks;
    public static final float e = (float) 0.1;
    public static final String TAG = "SENSOR";

    public SensorListener(GameCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    float[] r = new float[9];

    private void getDeviceOrientation() {
        SensorManager.getRotationMatrix(r, null, valuesAccel, valuesMagnet);
        SensorManager.getOrientation(r, valuesResult);



        Log.d(TAG, "y = " + valuesResult[1] + " x = " + valuesResult[2]);


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


    float[] valuesAccel = new float[3];
    float[] valuesMagnet = new float[3];
    float[] valuesResult = new float[3];

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
}
