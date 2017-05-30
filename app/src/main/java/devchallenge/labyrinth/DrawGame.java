package devchallenge.labyrinth;


import android.util.Log;


public class DrawGame extends Thread {
    private boolean running;

    private boolean pause;

    public static int FPS = 200;

    public final String TAG = getClass().getSimpleName();

    private GameCallbacks callbacks;


    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime = 10;
        while (running) {

            while (pause) {
                try {
                    Thread.sleep(sleepTime);
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
        start();

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
