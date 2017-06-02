package devchallenge.labyrinth.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import devchallenge.labyrinth.LoaderCallbacks;


public class SavedGamesAdapter extends RecyclerView.Adapter<SavedGamesAdapter.ViewHolder> {
    private List<String> data;

    private LoaderCallbacks callbackFile;

    public SavedGamesAdapter(List<String> data, LoaderCallbacks  callbackFile) {
        this.data = data;
        this.callbackFile = callbackFile;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, null);
        ViewHolder vh = new ViewHolder(textView);
        return vh;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String file = data.get(position);
        holder.fileName.setText(file);

        holder.fileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackFile.loadGame(file);
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView fileName;

        public ViewHolder(TextView view) {
            super(view);
            this.fileName = view;

        }

    }


}
