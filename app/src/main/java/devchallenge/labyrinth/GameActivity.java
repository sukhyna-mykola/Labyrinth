package devchallenge.labyrinth;

import android.annotation.TargetApi;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import devchallenge.labyrinth.dialogs.EndGameFragment;
import devchallenge.labyrinth.dialogs.PauseFragment;
import devchallenge.labyrinth.game.DrawGame;
import devchallenge.labyrinth.game.Game;
import devchallenge.labyrinth.game.GameCallbacks;
import devchallenge.labyrinth.helpers.GameSettings;
import devchallenge.labyrinth.models.direction.DirectionsEnum;
import devchallenge.labyrinth.views.LabyrinthView;

public class GameActivity extends AppCompatActivity implements
        View.OnClickListener, SensorCallbacks,
        View.OnTouchListener, GameCallbacks {

    private LabyrinthView v;
    private Game game;
    private ImageButton down, up, right, left;
    private DrawGame drawGame;
    private SensorListener listener;
    private RelativeLayout parent;
    private FrameLayout labyrinthContainer;

    public static int WIDTH, HEIGHT;

    public static final String LOAD_GAME = "LOAD_GAME";

    private SensorManager sensorManager;
    private Sensor sensorAccel;
    private Sensor sensorMagnet;

    private GameSettings settings;

    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onGlobalLayout() {

            HEIGHT = parent.getHeight();
            WIDTH = parent.getWidth();

            newGame();

            String filename = getIntent().getStringExtra(LOAD_GAME);

            if (filename != null)
                loadGame(filename);


            setLabirynthViewSize();

            drawGame.start();

            parent.getViewTreeObserver().removeOnGlobalLayoutListener(this);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_main, null);

        parent = (RelativeLayout) view.findViewById(R.id.parent);
        labyrinthContainer = (FrameLayout) view.findViewById(R.id.labyrinth_container);
        down = (ImageButton) view.findViewById(R.id.move_down);
        up = (ImageButton) view.findViewById(R.id.move_up);
        right = (ImageButton) view.findViewById(R.id.move_right);
        left = (ImageButton) view.findViewById(R.id.move_left);

        down.setOnTouchListener(this);
        right.setOnTouchListener(this);
        up.setOnTouchListener(this);
        left.setOnTouchListener(this);


        game = new Game(this);
        drawGame = new DrawGame(this);

        v = new LabyrinthView(this, game);
        labyrinthContainer.addView(v);

        parent.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);


        settings = GameSettings.getInstance(this);
        listener = new SensorListener(this);
        setContentView(view);


    }

    @Override
    public void registerSensors() {
        sensorManager.registerListener(listener, sensorAccel, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listener, sensorMagnet, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void unregisterSensors() {
        sensorManager.unregisterListener(listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseGame();
    }

    @Override
    public void update() {
        game.update();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                v.invalidate();
            }
        });

    }

    @Override
    public void newGame() {
        game.newGame();
    }

    @Override
    public void exitGame() {

        drawGame.setPause(false);
        drawGame.setRunning(false);

        finish();
    }


    @Override
    public void endGame() {
        drawGame.setPause(true);
        EndGameFragment.newInstance().show(getSupportFragmentManager(), "END_GAME");
    }

    @Override
    public void pauseGame() {
        drawGame.setPause(true);
        if (!settings.getController().equals(settings.getDefaultController())) {
            unregisterSensors();
        }
        PauseFragment.newInstance().show(getSupportFragmentManager(), "PAUSE_DIALOG");
    }

    @Override
    public void startGame() {
        drawGame.setPause(false);
        if (!settings.getController().equals(settings.getDefaultController())) {
            registerSensors();
        }
    }


    @Override
    public void loadGame(String filename) {

        game.loadGame(filename);
        setLabirynthViewSize();
        startGame();
    }

    @Override
    public void saveGame(String filename) {
        game.saveGame(filename);
    }


    @Override
    public void changeDirection(DirectionsEnum direction) {
        game.changeDirection(direction);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (v.getId()) {
                    case R.id.move_down:
                        changeDirection(DirectionsEnum.DOWN);
                        break;
                    case R.id.move_left:
                        changeDirection(DirectionsEnum.LEFT);

                        break;
                    case R.id.move_right:
                        changeDirection(DirectionsEnum.RIGHT);

                        break;
                    case R.id.move_up:
                        changeDirection(DirectionsEnum.UP);

                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                changeDirection(DirectionsEnum.NONE);

                break;
        }

        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.pause_button:
                pauseGame();
                break;
        }
    }

    private void setLabirynthViewSize() {
        v.setLayoutParams(new FrameLayout.LayoutParams(game.getSize() * game.getColumnCount(),
                game.getSize() * game.getRowCount()));
    }
}
