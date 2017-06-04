package devchallenge.labyrinth.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

import devchallenge.labyrinth.game.Game;
import devchallenge.labyrinth.models.Cell;

public class LabyrinthView extends View {

    private Game game;

    private Paint p;

    public LabyrinthView(Context context, Game game) {
        super(context);
        this.game = game;
        this.p = new Paint();
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public LabyrinthView(Context context) {
        super(context);
    }


    public void update() {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //розв’язок лабіринту
        if (game.getSolvedLabyrinth() != null) {
            for (Cell c : game.getSolvedLabyrinth()) {
                c.draw(canvas, p);
            }
        }
        //лабіринт
        for (int i = 0; i < game.getRowCount(); i++)
            for (int j = 0; j < game.getColumnCount(); j++)
                if (game.getLabyrinth()[i][j] != null)
                    game.getLabyrinth()[i][j].draw(canvas, p);

        //кулька
        game.getBall().draw(canvas, p);
    }

}
