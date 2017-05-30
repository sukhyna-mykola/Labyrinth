package devchallenge.labyrinth;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import devchallenge.labyrinth.dialogs.EndGameFragment;
import devchallenge.labyrinth.dialogs.PauseFragment;
import devchallenge.labyrinth.models.direction.DirectionsEnum;
import devchallenge.labyrinth.models.direction.DownDirection;
import devchallenge.labyrinth.models.direction.LeftDirection;
import devchallenge.labyrinth.models.direction.NoneDirection;
import devchallenge.labyrinth.models.direction.RightDirection;
import devchallenge.labyrinth.models.direction.UpDirection;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        View.OnTouchListener, GameCallbacks {
    private static final String TAG = "MAIN";
    private static final String PAUSE_DIALOG = "PAUSE_DIALOG";
    private LabyrinthView v;
    private Game game;
    private ImageButton down, up, right, left;
    private DrawGame drawGame;
    private SensorListener listener;
    private RelativeLayout parent;
    private FrameLayout labyrinthContainer;

    private Context context;

    SensorManager sensorManager;
    Sensor sensorAccel;
    Sensor sensorMagnet;

    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onGlobalLayout() {
            int height = parent.getHeight();
            int width = parent.getWidth();


            int min = Math.min(width / game.getColumnCount(), height / game.getRowCount());
            game.init(min);
            v.setLayoutParams(new FrameLayout.LayoutParams(min * game.getColumnCount(), min * game.getRowCount()));
            drawGame = new DrawGame((GameCallbacks) context);

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

        context = this;

        game = new Game(31, 31, this);
        v = new LabyrinthView(this, game);

        labyrinthContainer.addView(v);

        parent.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);


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
        unregisterSensors();
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
        finish();
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
    public void endGame() {
        drawGame.setPause(true);
        EndGameFragment.newInstance().show(getSupportFragmentManager(), "END_GAME");
    }

    @Override
    public void pauseGame() {
        drawGame.setPause(true);
        PauseFragment.newInstance().show(getSupportFragmentManager(), PAUSE_DIALOG);
    }

    @Override
    public void startGame() {
        drawGame.setPause(false);
    }

    @Override
    public void loadGame() {

    }

    @Override
    public void saveGame() {

    }

    @Override
    public void changeDirection(DirectionsEnum direction) {
        if (game.getDirection() != direction) {
            Log.d(TAG, "direction =  " + direction);
            switch (direction) {
                case DOWN:
                    game.changeDirection(new DownDirection(game.getBall()));
                    break;
                case UP:
                    game.changeDirection(new UpDirection(game.getBall()));
                    break;
                case LEFT:
                    game.changeDirection(new LeftDirection(game.getBall()));
                    break;
                case RIGHT:
                    game.changeDirection(new RightDirection(game.getBall()));
                    break;
                case NONE:
                    game.changeDirection(new NoneDirection(game.getBall()));
                    break;
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.pause_button:
                Toast.makeText(context, "PAUSE", Toast.LENGTH_SHORT).show();
                pauseGame();
                break;
        }
    }
}
