package devchallenge.labyrinth.helpers;

import android.content.Context;
import android.graphics.Color;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import devchallenge.labyrinth.callbacks.JsonLoader;
import devchallenge.labyrinth.game.Game;
import devchallenge.labyrinth.models.Ball;
import devchallenge.labyrinth.models.Border;
import devchallenge.labyrinth.models.Cell;
import devchallenge.labyrinth.models.LabelCell;


public class GameSaver {

    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String CELLS = "cells";

    public static final String BALL = "ball";

    public static final String ROW = "row";
    public static final String COLUMN = "column";

    public static final String TYPE = "type";

    public static final String JSON = "JSON_";


    private JsonLoader jsonLoader;
    private JsonHelper jsonHelper;

    private static GameSaver saver;

    private Context context;

    public GameSaver(Context context) {

        this.context = context;
        this.jsonHelper = new JsonHelper();
        this.jsonLoader = PrefenceHelper.getInstance(context);
    }

    public static GameSaver getInstance(Context context) {
        if (saver == null)
            saver = new GameSaver(context);
        return saver;
    }



    public void load(String fileName, Game game) throws JSONException {

        JSONObject savedGame = jsonLoader.loadJson(fileName);
        jsonHelper.labyrinthFromJson(savedGame, game);
    }

    public void save(String fileName, Game game) throws JSONException {
        JSONObject savingGame = jsonHelper.labyrinthToJson(game);
        jsonLoader.saveJson(savingGame, fileName);


    }

    public List<String> loadAll() {
        return jsonLoader.getAllJson(JSON);
    }

    public void remove(String fileName) {
        jsonLoader.removeJson(fileName);
    }


    private class JsonHelper {
        //---------------------------------------
        // saving game

        private JSONObject labyrinthToJson(Game game) throws JSONException {
            JSONObject o = new JSONObject();
            o.put(WIDTH, game.getColumnCount());
            o.put(HEIGHT, game.getRowCount());

            JSONObject ball = cellToJson(game.getBall());
            o.put(BALL, ball);

            JSONArray array = new JSONArray();
            for (int i = 0; i < game.getRowCount(); i++) {
                for (int j = 0; j < game.getColumnCount(); j++) {
                    Cell cell = game.getLabyrinth()[i][j];
                    if (cell != null) {
                        array.put(cellToJson(cell));
                    }

                }
            }
            o.put(CELLS, array);

            return o;
        }


        private JSONObject cellToJson(Cell cell) throws JSONException {
            JSONObject o = new JSONObject();

            o.put(ROW, cell.getRow());
            o.put(COLUMN, cell.getColumn());
            o.put(TYPE, cell.getType());

            return o;
        }


        //---------------------------------------
        // loading game

        private void labyrinthFromJson(JSONObject o, Game game) throws JSONException {
            int width = o.getInt(WIDTH);
            int height = o.getInt(HEIGHT);

            game.setRowCount(height);
            game.setColumnCount(width);

            game.calculateCellSize();

            Cell[][] cells = new Cell[width][height];

            JSONArray a = o.getJSONArray(CELLS);
            for (int i = 0; i < a.length(); i++) {
                JSONObject object = a.getJSONObject(i);

                Cell c = cellFromJson(object, game);

                cells[c.getRow()][c.getColumn()] = c;

            }

            game.setLabyrinth(cells);

            JSONObject ball = o.getJSONObject(BALL);

            game.setBall((Ball) cellFromJson(ball, game));


        }


        private Cell cellFromJson(JSONObject o, Game game) throws JSONException {

            int cellSize = game.getSize();

            Cell c;

            String type = o.getString(TYPE);
            int row = o.getInt(ROW);
            int column = o.getInt(COLUMN);

            if (type.equals(Ball.BALL_CELL)) {
                c = new Ball(row, column, cellSize, cellSize);
            } else if (type.equals(LabelCell.LABEL_CELL_START)) {
                c = new LabelCell(row, column, cellSize, cellSize, Color.WHITE, type);
                game.setStartCell((LabelCell) c);
            } else if (type.equals(LabelCell.LABEL_CELL_FINISH)) {
                c = new LabelCell(row, column, cellSize, cellSize, Color.GREEN, type);
                game.setEndCell((LabelCell) c);
            } else {
                c = new Border(row, column, cellSize, cellSize);
            }

            return c;
        }
    }
}
