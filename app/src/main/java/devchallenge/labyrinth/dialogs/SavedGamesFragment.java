package devchallenge.labyrinth.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import devchallenge.labyrinth.LoaderCallbacks;
import devchallenge.labyrinth.R;
import devchallenge.labyrinth.adapters.SavedGamesAdapter;
import devchallenge.labyrinth.helpers.GameSaver;


public class SavedGamesFragment extends DialogFragment implements LoaderCallbacks {

    private LoaderCallbacks  callback;

    public static final String SAVED_GAMES_DIALOG  ="SAVED_GAMES_DIALOG";


    private RecyclerView list;
    private RecyclerView.Adapter adapter;

    public static SavedGamesFragment newInstance() {

        Bundle args = new Bundle();

        SavedGamesFragment fragment = new SavedGamesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_saved_games, null);

        list = (RecyclerView) v.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SavedGamesAdapter(GameSaver.getInstance(getContext()).loadAll(), this);

        list.setAdapter(adapter);


        return new AlertDialog.Builder(getContext()).
                setView(v)
             /*   .setPositiveButton("OK", null)
                .setNegativeButton("CANCEL", null)*/
                .setTitle("Pick file")
                .create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoaderCallbacks ) {
            callback = (LoaderCallbacks ) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement LoaderCallbacks ");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }


    @Override
    public void loadGame(String filename) {
        callback.loadGame(filename);
        dismiss();
    }

    @Override
    public void saveGame(String filename) {

    }

}
