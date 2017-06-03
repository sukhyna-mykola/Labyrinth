package devchallenge.labyrinth.dialogs;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import devchallenge.labyrinth.callbacks.LoaderCallbacks;
import devchallenge.labyrinth.R;


public class SaveDialog extends DialogSimple {
    private LoaderCallbacks callback;

    public static final String SAVE_DIALOG = "SAVE_DIALOG";

    public static SaveDialog newInstance() {

        Bundle args = new Bundle();

        SaveDialog fragment = new SaveDialog();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View configureDialogView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.save_dialog, null);
        final EditText editText = (EditText) v.findViewById(R.id.fileNameInputField);

        v.findViewById(R.id.positive_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.saveGame(editText.getText().toString());
                dismiss();
            }
        });

        v.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return  v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoaderCallbacks) {
            callback = (LoaderCallbacks) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement LoaderCallbacks");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }


}
