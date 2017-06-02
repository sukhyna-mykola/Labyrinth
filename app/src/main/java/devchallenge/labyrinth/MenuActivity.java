package devchallenge.labyrinth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import devchallenge.labyrinth.dialogs.SavedGamesFragment;
import devchallenge.labyrinth.dialogs.SettingsFragment;

import static devchallenge.labyrinth.dialogs.SavedGamesFragment.SAVED_GAMES_DIALOG;
import static devchallenge.labyrinth.dialogs.SettingsFragment.SETTINGS_DIALOG;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener, LoaderCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_game:
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                break;
            case R.id.load_game:
                SavedGamesFragment.newInstance().show(getSupportFragmentManager(), SAVED_GAMES_DIALOG);
                break;
            case R.id.settings:
                SettingsFragment.newInstance().show(getSupportFragmentManager(), SETTINGS_DIALOG);
                break;
            case R.id.exit:
                break;
        }
    }

    @Override
    public void loadGame(String filename) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameActivity.LOAD_GAME, filename);
        startActivity(intent);
    }

    @Override
    public void saveGame(String filename) {
        //--------------------------
    }
}
