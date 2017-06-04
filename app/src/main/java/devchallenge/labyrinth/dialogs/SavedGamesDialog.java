package devchallenge.labyrinth.dialogs;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import devchallenge.labyrinth.R;
import devchallenge.labyrinth.adapters.SavedGamesAdapter;
import devchallenge.labyrinth.callbacks.LoaderCallbacks;
import devchallenge.labyrinth.helpers.GameSaver;


public class SavedGamesDialog extends DialogSimple implements LoaderCallbacks {

    private LoaderCallbacks callback;

    public static final String SAVED_GAMES_DIALOG = "SAVED_GAMES_DIALOG";


    private RecyclerView list;
    private RecyclerView.Adapter adapter;

    public static SavedGamesDialog newInstance() {

        Bundle args = new Bundle();

        SavedGamesDialog fragment = new SavedGamesDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new SavedGamesAdapter(getContext(), GameSaver.getInstance(getContext()).loadAll(), this);
    }

    @Override
    public View configureDialogView() {

        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_saved_games, null);

        list = (RecyclerView) v.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(adapter);

        v.findViewById(R.id.cencel_button).setOnClickListener(onClickListener);
        v.findViewById(R.id.close_button).setOnClickListener(onClickListener);

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoaderCallbacks) {
            callback = (LoaderCallbacks) context;
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


        //закрити всі відкриті діалоги і почати гру
        FragmentManager m = ((AppCompatActivity) getContext()).getSupportFragmentManager();
        for (Fragment f : m.getFragments()) {
            if (f instanceof DialogFragment)
                ((DialogFragment) f).dismiss();
        }
    }

    @Override
    public void saveGame(String filename) {
        //-----------------
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };


}
