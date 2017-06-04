package devchallenge.labyrinth.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import devchallenge.labyrinth.R;
import devchallenge.labyrinth.callbacks.GameCallbacks;

import static devchallenge.labyrinth.dialogs.SavedGamesDialog.SAVED_GAMES_DIALOG;
import static devchallenge.labyrinth.dialogs.SettingsDialog.SETTINGS_DIALOG;


public class EndGameDialog extends DialogSimple {

    public static final String END_GAME_DIALOG = "END_GAME_DIALOG";

    private GameCallbacks callbacks;

    public static EndGameDialog newInstance() {
        EndGameDialog fragment = new EndGameDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View configureDialogView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_end_game, null);
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
        v.findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsDialog.newInstance().show(((AppCompatActivity) getContext()).getSupportFragmentManager(), SETTINGS_DIALOG);
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
