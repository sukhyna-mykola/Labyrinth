package devchallenge.labyrinth.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import devchallenge.labyrinth.R;
import devchallenge.labyrinth.callbacks.LoaderCallbacks;
import devchallenge.labyrinth.helpers.GameSaver;


public class SavedGamesAdapter extends RecyclerView.Adapter<SavedGamesAdapter.ViewHolder> {
    private List<String> data;
    private Context context;
    private LoaderCallbacks callbacks;

    public SavedGamesAdapter(Context context, List<String> data, LoaderCallbacks callbacks) {
        this.context = context;
        this.data = data;
        this.callbacks = callbacks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_game, null);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String name = data.get(position);
        holder.nameSavedGame.setText(name);
        holder.deleteSavedGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameSaver.getInstance(context).remove(name);
                data.remove(name);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            }
        });

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.loadGame(name);
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameSavedGame;
        public ImageButton deleteSavedGame;
        public View v;

        public ViewHolder(View view) {
            super(view);
            this.v = view;
            this.nameSavedGame = (TextView) v.findViewById(R.id.save_game_name);
            this.deleteSavedGame = (ImageButton) v.findViewById(R.id.delete_saved_game);

        }

    }


}
