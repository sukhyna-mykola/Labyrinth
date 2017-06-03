package devchallenge.labyrinth.callbacks;


import org.json.JSONObject;

import java.util.List;

public interface JsonLoader {

    void saveJson(JSONObject jsonObject, String fileName);

    JSONObject loadJson(String fileName);

    List<String> getAllJson(String startWith);

    void removeJson(String fileName);
}
