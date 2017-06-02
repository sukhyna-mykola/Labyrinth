package devchallenge.labyrinth;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Surface;

import devchallenge.labyrinth.game.GameCallbacks;
import devchallenge.labyrinth.models.direction.DirectionsEnum;

public class SensorListener implements SensorEventListener {
    private GameCallbacks callbacks;
    public static final float e = (float) 0.1;
    public static final String TAG = "SENSOR";

    public SensorListener(GameCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    int rotation;
    float[] r = new float[9];

    private void getDeviceOrientation() {
        SensorManager.getRotationMatrix(r, null, valuesAccel, valuesMagnet);
        SensorManager.getOrientation(r, valuesResult);

   /*     valuesResult[0] = (float) Math.toDegrees(valuesResult[0]);
        valuesResult[1] = (float) Math.toDegrees(valuesResult[1]);
        valuesResult[2] = (float) Math.toDegrees(valuesResult[2]);*/


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

    float[] inR = new float[9];
    float[] outR = new float[9];

    void getActualDeviceOrientation() {
        SensorManager.getRotationMatrix(inR, null, valuesAccel, valuesMagnet);
        int x_axis = SensorManager.AXIS_X;
        int y_axis = SensorManager.AXIS_Y;
        switch (rotation) {
            case (Surface.ROTATION_0):
                break;
            case (Surface.ROTATION_90):
                x_axis = SensorManager.AXIS_Y;
                y_axis = SensorManager.AXIS_MINUS_X;
                break;
            case (Surface.ROTATION_180):
                y_axis = SensorManager.AXIS_MINUS_Y;
                break;
            case (Surface.ROTATION_270):
                x_axis = SensorManager.AXIS_MINUS_Y;
                y_axis = SensorManager.AXIS_X;
                break;
            default:
                break;
        }
        SensorManager.remapCoordinateSystem(inR, x_axis, y_axis, outR);
        SensorManager.getOrientation(outR, valuesResult2);
        valuesResult2[0] = (float) Math.toDegrees(valuesResult2[0]);
        valuesResult2[1] = (float) Math.toDegrees(valuesResult2[1]);
        valuesResult2[2] = (float) Math.toDegrees(valuesResult2[2]);
        return;
    }

    float[] valuesAccel = new float[3];
    float[] valuesMagnet = new float[3];
    float[] valuesResult = new float[3];
    float[] valuesResult2 = new float[3];

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
