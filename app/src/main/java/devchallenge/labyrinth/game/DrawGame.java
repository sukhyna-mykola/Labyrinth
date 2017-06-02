package devchallenge.labyrinth.game;


import android.util.Log;

import devchallenge.labyrinth.game.GameCallbacks;


public class DrawGame extends Thread {
    private boolean running;

    private boolean pause;

    public static int FPS = 60;

    public final String TAG = getClass().getSimpleName();

    private GameCallbacks callbacks;


    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime,sleepTime;

        while (running) {

            while (pause) {
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            startTime = System.currentTimeMillis();

            callbacks.update();

            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            Log.d(TAG, "sleep time = " + sleepTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
            } catch (Exception e) {
            }

        }
    }

    public DrawGame(GameCallbacks callbacks) {
        this.callbacks = callbacks;
        //  pause = true;
        running = true;

    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

}
