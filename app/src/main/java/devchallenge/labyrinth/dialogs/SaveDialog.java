package devchallenge.labyrinth.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import devchallenge.labyrinth.LoaderCallbacks;
import devchallenge.labyrinth.R;


public class SaveDialog extends DialogFragment {
    private LoaderCallbacks callback;

    public static final String SAVE_DIALOG = "SAVE_DIALOG";

    public static SaveDialog newInstance() {

        Bundle args = new Bundle();

        SaveDialog fragment = new SaveDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getContext()).inflate(R.layout.save_dialog, null);
        final EditText editText = (EditText) v.findViewById(R.id.fileNameInputField);

        return new AlertDialog.Builder(getContext())
                .setView(v)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.saveGame(editText.getText().toString());
                        dismiss();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setTitle("Input file name")
                .create();
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
