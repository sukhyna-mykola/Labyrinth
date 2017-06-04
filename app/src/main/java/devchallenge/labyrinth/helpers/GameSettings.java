package devchallenge.labyrinth.helpers;


import android.content.Context;

import devchallenge.labyrinth.R;
import devchallenge.labyrinth.callbacks.SettingsLoader;

public class GameSettings {

    public static final String X = "x";
    public static final String SIZE_KEY = "SIZE_KEY";
    public static final String MODE_KEY = "MODE_KEY";
    public static final String CONTROLLER_KEY = "CONTROLLER_KEY";

    private String[] sizes, modes, controllers;

    private String defaultSize, defaultMode, defaultController;

    private SettingsLoader loader;
    private int rowCount, columnCount;
    private String size;
    private String mode;
    private String controller;

    public static GameSettings gameSettings;

    public static GameSettings getInstance(Context c) {
        if (gameSettings == null)
            gameSettings = new GameSettings(c);
        return gameSettings;
    }

    private GameSettings(Context c) {
        loader = PrefenceHelper.getInstance(c);

        sizes = c.getResources().getStringArray(R.array.sizes);
        modes = c.getResources().getStringArray(R.array.modes);
        controllers = c.getResources().getStringArray(R.array.controllers);

        defaultSize = sizes[0];
        defaultMode = modes[0];
        defaultController = controllers[0];

        loadController();
        loadMode();
        loadSize();

    }

    public void saveMode(String mode) {
        this.mode = mode;
        loader.saveMode(mode);
    }

    public void loadMode() {
        this.mode = loader.loadMode(defaultMode);
    }

    public void saveController(String controller) {
        this.controller = controller;
        loader.saveController(controller);
    }

    public void loadController() {
        this.controller = loader.loadController(defaultController);
    }


    public void saveSize(String size) {
        this.size = size;
        String sizeArr[] = size.split(X);
        rowCount = Integer.parseInt(sizeArr[0]);
        columnCount = Integer.parseInt(sizeArr[1]);

        loader.saveSize(size);
    }

    public String getSize() {
        return size;
    }

    public String[] getSizes() {

        return sizes;
    }

    public String[] getModes() {
        return modes;
    }

    public String[] getControllers() {
        return controllers;
    }

    public String getDefaultSize() {
        return defaultSize;
    }

    public String getDefaultMode() {
        return defaultMode;
    }

    public String getDefaultController() {
        return defaultController;
    }

    public void loadSize() {
        this.size = loader.loadSize(defaultSize);
        String sizeArr[] = size.split(X);
        this.rowCount = Integer.parseInt(sizeArr[0]);
        this.columnCount = Integer.parseInt(sizeArr[1]);
    }

    public int getRowCount() {
        return rowCount;
    }


    public int getColumnCount() {
        return columnCount;
    }


    public String getMode() {
        return mode;
    }


    public String getController() {
        return controller;
    }

}
