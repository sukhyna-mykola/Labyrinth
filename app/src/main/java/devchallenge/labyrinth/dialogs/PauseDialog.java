package devchallenge.labyrinth.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import devchallenge.labyrinth.R;
import devchallenge.labyrinth.callbacks.GameCallbacks;

import static devchallenge.labyrinth.dialogs.SaveDialog.SAVE_DIALOG;
import static devchallenge.labyrinth.dialogs.SavedGamesDialog.SAVED_GAMES_DIALOG;


public class PauseDialog extends DialogSimple {


    public static final String PAUSE_DIALOG = "PAUSE_DIALOG";

    private GameCallbacks callbacks;

    public static PauseDialog newInstance() {
        PauseDialog fragment = new PauseDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View configureDialogView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_pause, null);
        v.findViewById(R.id.resume_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.startGame();
                dismiss();
            }
        });

        v.findViewById(R.id.new_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.newGame();
                dismiss();
            }
        });
        v.findViewById(R.id.load_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavedGamesDialog.newInstance().show(((AppCompatActivity) getContext()).getSupportFragmentManager(), SAVED_GAMES_DIALOG);
            }
        });
        v.findViewById(R.id.save_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveDialog.newInstance().show(((AppCompatActivity) getContext()).getSupportFragmentManager(), SAVE_DIALOG);
            }
        });

        v.findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.exitGame();
                dismiss();
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GameCallbacks) {
            callbacks = (GameCallbacks) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement GameCallbacks");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }


}
