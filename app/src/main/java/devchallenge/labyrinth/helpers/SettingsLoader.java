package devchallenge.labyrinth.helpers;


public interface SettingsLoader {

    void saveSize(String size);

    String loadSize(String defaultValue);

    void saveMode(String mode);

    String loadMode(String defaultValue);

    void saveController(String size);

    String loadController(String defaultValue);
}
