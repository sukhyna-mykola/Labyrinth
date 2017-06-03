package devchallenge.labyrinth.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import devchallenge.labyrinth.R;
import devchallenge.labyrinth.helpers.GameSettings;


public class SettingsDialog extends DialogSimple {

    public static final String SETTINGS_DIALOG = "SETTINGS_DIALOG";

    private Spinner modeSpinner, sizeSpinner, controllerSpinner;

    private ArrayAdapter<String> sizeAdapter, modeAdapter, controllerAdapter;

    private GameSettings settings;

    public static SettingsDialog newInstance() {
        SettingsDialog fragment = new SettingsDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = GameSettings.getInstance(getContext());
    }


    @Override
    public View configureDialogView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_settings, null);

        sizeSpinner = (Spinner) v.findViewById(R.id.spinner_size);
        modeSpinner = (Spinner) v.findViewById(R.id.spinner_mode);
        controllerSpinner = (Spinner) v.findViewById(R.id.spinner_controller);

        sizeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, settings.getSizes());
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);
        sizeSpinner.setSelection(sizeAdapter.getPosition(settings.getSize()), true);
        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                settings.saveSize(sizeAdapter.getItem(position));
                sizeSpinner.setPrompt(settings.getSize());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        modeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, settings.getModes());
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(modeAdapter);
        modeSpinner.setSelection(modeAdapter.getPosition(settings.getMode()), true);
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                settings.saveMode(modeAdapter.getItem(position));
                modeSpinner.setPrompt(settings.getMode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        controllerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, settings.getControllers());
        controllerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        controllerSpinner.setAdapter(controllerAdapter);
        controllerSpinner.setSelection(controllerAdapter.getPosition(settings.getController()), true);
        controllerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                settings.saveController(controllerAdapter.getItem(position));
                controllerSpinner.setPrompt(settings.getController());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        v.findViewById(R.id.positive_button).setOnClickListener(onClickListener);
        v.findViewById(R.id.close_button).setOnClickListener(onClickListener);

        return v;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

}
