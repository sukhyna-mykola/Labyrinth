package devchallenge.labyrinth;

import android.annotation.TargetApi;
import android.content.Context;
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
import android.widget.Toast;

import devchallenge.labyrinth.models.direction.Direction;

public class MainActivity extends AppCompatActivity implements UpdateCallback, View.OnTouchListener, GameCallbacks {
    private LabyrinthView v;
    private Game game;
    private ImageButton down, up, right, left;
    private DrawGame drawGame;
    private RelativeLayout parent;
    private FrameLayout labyrinthContainer;
    private UpdateCallback updateCallback;
    private Context context;

    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onGlobalLayout() {
            int height = parent.getHeight();
            int width = parent.getWidth();


            int min = Math.min(width / game.getColumnCount(), height / game.getRowCount());
            game.init(min);
            v.setLayoutParams(new FrameLayout.LayoutParams(min * game.getColumnCount(), min * game.getRowCount()));
            drawGame = new DrawGame(updateCallback);

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

        updateCallback = this;
        context = this;

        game = new Game(31, 31,this);
        v = new LabyrinthView((Context) updateCallback, game);

        labyrinthContainer.addView(v);

        parent.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);

        setContentView(view);


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
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (v.getId()) {
                    case R.id.move_down:
                        game.getBall().setDirection(Direction.DOWN);
                        break;
                    case R.id.move_left:
                        game.getBall().setDirection(Direction.LEFT);
                        break;
                    case R.id.move_right:
                        game.getBall().setDirection(Direction.RIGHT);
                        break;
                    case R.id.move_up:
                        game.getBall().setDirection(Direction.UP);
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                game.getBall().setDirection(Direction.NONE);
                break;
        }

        return true;
    }

    @Override
    public void endGame() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "END GAME", Toast.LENGTH_LONG).show();
            }
        });

    }
}
