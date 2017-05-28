package devchallenge.labyrinth;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

public class LabyrinthView extends View {

    private Game game;

    public LabyrinthView(Context context, Game game) {
        super(context);
        this.game = game;
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public LabyrinthView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        for (int i = 0; i < game.getRowCount(); i++)
            for (int j = 0; j < game.getColumnCount(); j++)
                if (game.getLabyrinth()[i][j] != null)
                    game.getLabyrinth()[i][j].draw(canvas, p);
        game.getBall().draw(canvas, p);
    }

}
