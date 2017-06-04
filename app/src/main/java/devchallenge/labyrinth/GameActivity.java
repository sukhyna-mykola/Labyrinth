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
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import devchallenge.labyrinth.callbacks.GameCallbacks;
import devchallenge.labyrinth.callbacks.SensorCallbacks;
import devchallenge.labyrinth.dialogs.EndGameDialog;
import devchallenge.labyrinth.dialogs.PauseDialog;
import devchallenge.labyrinth.game.DrawGame;
import devchallenge.labyrinth.game.Game;
import devchallenge.labyrinth.helpers.GameSettings;
import devchallenge.labyrinth.models.direction.DirectionsEnum;
import devchallenge.labyrinth.views.LabyrinthView;

import static devchallenge.labyrinth.dialogs.EndGameDialog.END_GAME_DIALOG;
import static devchallenge.labyrinth.dialogs.PauseDialog.PAUSE_DIALOG;

public class GameActivity extends AppCompatActivity implements
        View.OnClickListener, SensorCallbacks,
        View.OnTouchListener, GameCallbacks {

    public static final String LOAD_GAME = "LOAD_GAME";

    public static int WIDTH, HEIGHT;

    private LabyrinthView labyrinthView;
    private ImageButton down, up, right, left;
    private ImageButton solve, controller;
    private RelativeLayout parent;
    private FrameLayout labyrinthContainer;
    private GridLayout controllerView;

    private Game game;
    private DrawGame drawGame;

    private SensorListener listener;
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
            setControllerButtonImage();

            drawGame.start();

            parent.getViewTreeObserver().removeOnGlobalLayoutListener(this);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_game, null);

        parent = (RelativeLayout) view.findViewById(R.id.parent);
        labyrinthContainer = (FrameLayout) view.findViewById(R.id.labyrinth_container);

        controllerView = (GridLayout) view.findViewById(R.id.controll_view);

        down = (ImageButton) view.findViewById(R.id.move_down);
        up = (ImageButton) view.findViewById(R.id.move_up);
        right = (ImageButton) view.findViewById(R.id.move_right);
        left = (ImageButton) view.findViewById(R.id.move_left);

        solve = (ImageButton) view.findViewById(R.id.solve_button);
        controller = (ImageButton) view.findViewById(R.id.controller_button);

        down.setOnTouchListener(this);
        right.setOnTouchListener(this);
        up.setOnTouchListener(this);
        left.setOnTouchListener(this);


        game = new Game(this);
        drawGame = new DrawGame(this);

        labyrinthView = new LabyrinthView(this, game);
        labyrinthContainer.addView(labyrinthView);

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
    protected void onResume() {
        super.onResume();
        startGame();
    }

    @Override
    public void update() {
        game.update();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                labyrinthView.update();
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
        EndGameDialog.newInstance().show(getSupportFragmentManager(), END_GAME_DIALOG);
    }

    @Override
    public void pauseGame() {
        drawGame.setPause(true);
        if (!settings.getController().equals(settings.getDefaultController())) {
            unregisterSensors();
        }


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
    public void showSolve() {
        game.showSolve();
        solve.setImageResource(R.drawable.ic_visibility_off_black_24dp);
    }

    @Override
    public void hideSolve() {
        game.hideSolve();
        solve.setImageResource(R.drawable.ic_visibility_black_24dp);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setPressed(true);
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
                v.setPressed(false);
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
                PauseDialog.newInstance().show(getSupportFragmentManager(), PAUSE_DIALOG);
                break;

            case R.id.solve_button:
                if (game.getSolvedLabyrinth() == null) {
                    showSolve();
                } else {
                    hideSolve();
                }
                break;

            case R.id.controller_button:
                if (settings.getController().equals(settings.getDefaultController())) {
                    settings.saveController(settings.getControllers()[1]);
                } else {
                    settings.saveController(settings.getControllers()[0]);
                }
                setControllerButtonImage();
                break;
        }
    }

    private void setLabirynthViewSize() {
        labyrinthView.setLayoutParams(new FrameLayout.LayoutParams(game.getSize() * game.getColumnCount(),
                game.getSize() * game.getRowCount()));
    }

    private void setControllerButtonImage() {
        if (settings.getController().equals(settings.getDefaultController())) {
            controller.setImageResource(R.drawable.ic_screen_rotation_black_24dp);
            setEnabledControlButtons(true);
            unregisterSensors();
        } else {
            controller.setImageResource(R.drawable.ic_screen_lock_rotation_black_24dp);
            setEnabledControlButtons(false);
            registerSensors();
        }
    }

    private void setEnabledControlButtons(boolean state) {
        for (int i = 0; i < controllerView.getChildCount(); i++) {
            controllerView.getChildAt(i).setEnabled(state);
        }

    }

}
