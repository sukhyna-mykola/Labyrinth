package devchallenge.labyrinth.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static devchallenge.labyrinth.helpers.GameSaver.JSON;
import static devchallenge.labyrinth.helpers.GameSettings.CONTROLLER_KEY;
import static devchallenge.labyrinth.helpers.GameSettings.MODE_KEY;
import static devchallenge.labyrinth.helpers.GameSettings.SIZE_KEY;


public class PrefenceHelper implements JsonLoader, SettingsLoader {
    private Context context;

    public static PrefenceHelper helper;
    private SharedPreferences sPref;

    private static final String PREFERENCE = "preferences";


    private PrefenceHelper(Context context) {
        this.context = context;
        sPref = context.getSharedPreferences(PREFERENCE, MODE_PRIVATE);
    }

    public static PrefenceHelper getInstance(Context c) {
        if (helper == null)
            helper = new PrefenceHelper(c);
        return helper;
    }


    @Override
    public void saveJson(JSONObject jsonObject, String fileName) {
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(JSON + fileName, jsonObject.toString());
        ed.commit();
    }

    @Override
    public JSONObject loadJson(String fileName) {
        String json = sPref.getString(JSON + fileName, "");
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getAllJson(String startWith) {
        List<String> result = new ArrayList<>();
        HashMap<String, ?> all = (HashMap) sPref.getAll();
        for (String s : all.keySet()) {
            if (s.startsWith(startWith))
                result.add(s.replaceFirst(JSON, ""));
        }
        return result;

    }

    @Override
    public void removeJson(String fileName) {
        SharedPreferences.Editor ed = sPref.edit();
        ed.remove(fileName);
        ed.commit();
    }


    @Override
    public void saveSize(String size) {
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SIZE_KEY, size);
        ed.commit();
    }

    @Override
    public String loadSize(String defaultValue) {
        return sPref.getString(SIZE_KEY, defaultValue);
    }

    @Override
    public void saveMode(String mode) {
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(MODE_KEY, mode);
        ed.commit();
    }

    @Override
    public String loadMode(String defaultValue) {
        return sPref.getString(MODE_KEY, defaultValue);
    }

    @Override
    public void saveController(String controller) {
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(CONTROLLER_KEY, controller);
        ed.commit();
    }

    @Override
    public String loadController(String defaultValue) {
        return sPref.getString(CONTROLLER_KEY, defaultValue);
    }

    public Map<String, ?> getAll() {
        return sPref.getAll();
    }
}
