package devchallenge.labyrinth.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import devchallenge.labyrinth.game.GameCallbacks;
import devchallenge.labyrinth.R;

import static devchallenge.labyrinth.dialogs.SavedGamesFragment.SAVED_GAMES_DIALOG;


public class EndGameFragment extends DialogFragment {

    private GameCallbacks callbacks;

    public static EndGameFragment newInstance() {
        EndGameFragment fragment = new EndGameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
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
                SavedGamesFragment.newInstance().show(((AppCompatActivity) getContext()).getSupportFragmentManager(), SAVED_GAMES_DIALOG);
                dismiss();
            }
        });

        v.findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.exitGame();
                dismiss();
            }
        });
        return new AlertDialog.Builder(getContext())
                .setView(v)
                .create();
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
