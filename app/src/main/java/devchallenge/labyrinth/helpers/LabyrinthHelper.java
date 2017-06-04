package devchallenge.labyrinth.helpers;


import android.graphics.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import devchallenge.labyrinth.models.Border;
import devchallenge.labyrinth.models.Cell;
import devchallenge.labyrinth.models.LabelCell;
import devchallenge.labyrinth.models.direction.DirectionsEnum;

import static devchallenge.labyrinth.models.direction.DirectionsEnum.DOWN;
import static devchallenge.labyrinth.models.direction.DirectionsEnum.LEFT;
import static devchallenge.labyrinth.models.direction.DirectionsEnum.RIGHT;
import static devchallenge.labyrinth.models.direction.DirectionsEnum.UP;

public class LabyrinthHelper {

    private final static List<DirectionsEnum> directions = new ArrayList<>(EnumSet.of(UP, RIGHT, DOWN, LEFT));

    private static LabyrinthHelper labyrinyhHelper;

    public static LabyrinthHelper getInstance() {
        if (labyrinyhHelper == null)
            labyrinyhHelper = new LabyrinthHelper();
        return labyrinyhHelper;
    }

    public Cell[][] generateLabyrinth(int rowCount, int columnCount, int width, int height) {
        Cell[][] labyrinth = new Cell[rowCount][columnCount];

        for (int i = 0; i < rowCount; i++)
            for (int j = 0; j < columnCount; j++)
                labyrinth[i][j] = new Border(i, j, width, height);

        build(1, 1, labyrinth);

        return labyrinth;
    }


    private void build(int r, int c, Cell[][] labyrinth) {

        Collections.shuffle(directions);

        for (DirectionsEnum d : directions) {
            switch (d) {
                case UP:
                    if (r - 2 <= 0)
                        continue;
                    if (labyrinth[r - 2][c] != null) {
                        labyrinth[r - 2][c] = null;
                        labyrinth[r - 1][c] = null;
                        build(r - 2, c, labyrinth);
                    }
                    break;
                case RIGHT:
                    if (c + 2 >= labyrinth[0].length - 1)
                        continue;
                    if (labyrinth[r][c + 2] != null) {
                        labyrinth[r][c + 2] = null;
                        labyrinth[r][c + 1] = null;
                        build(r, c + 2, labyrinth);
                    }
                    break;
                case DOWN:
                    if (r + 2 >= labyrinth.length - 1)
                        continue;
                    if (labyrinth[r + 2][c] != null) {
                        labyrinth[r + 2][c] = null;
                        labyrinth[r + 1][c] = null;
                        build(r + 2, c, labyrinth);
                    }
                    break;
                case LEFT:
                    if (c - 2 <= 0)
                        continue;
                    if (labyrinth[r][c - 2] != null) {
                        labyrinth[r][c - 2] = null;
                        labyrinth[r][c - 1] = null;
                        build(r, c - 2, labyrinth);
                    }
                    break;
            }
        }

    }


    private Node startNode, finishNode;
    private Queue<Node> nodes = new LinkedList<>();

    public List<Cell> solve(Cell[][] l, Cell start, Cell finish) {

        nodes.clear();

        int size = start.getHeight();

        Node[][] n = new Node[l.length][l[0].length];

        for (int i = 0; i < l.length; i++) {
            for (int j = 0; j < l[0].length; j++) {
                if (l[i][j] == null) {
                    n[i][j] = new Node(i, j);
                }
            }
        }

        startNode = new Node(start.getRow(), start.getColumn());
        n[startNode.getRow()][startNode.getColumn()] = startNode;

        finishNode = new Node(finish.getRow(), finish.getColumn());
        n[finishNode.getRow()][finishNode.getColumn()] = finishNode;

        List<Cell> res = new ArrayList<>();

        for (Node node : bfs(startNode, n)) {
            res.add(new LabelCell(node.getRow(), node.getColumn(), size, size, Color.YELLOW, "SOLVE_CELL"));
        }


        return res;
    }


    private List<Node> bfs(Node v, Node[][] l) {    //пошук в ширину

        nodes.add(v);
        v.visited = true;
        while (!nodes.isEmpty()) {
            v = nodes.poll();

            if (v.column == finishNode.column && v.row == finishNode.row)
                break;

            //up
            if (v.row > 0)
                if (l[v.row - 1][v.column] != null) {
                    Node w = l[v.row - 1][v.column];
                    if (!w.visited) {
                        nodes.add(w);
                        w.visited = true;
                        w.parentNode = v;

                    }
                }

            //down
            if (v.row < l.length - 1)
                if (l[v.row + 1][v.column] != null) {
                    Node w = l[v.row + 1][v.column];
                    if (!w.visited) {
                        nodes.add(w);
                        w.visited = true;
                        w.parentNode = v;
                    }
                }
            //right
            if (v.column < l[0].length - 1)
                if (l[v.row][v.column + 1] != null) {
                    Node w = l[v.row][v.column + 1];
                    if (!w.visited) {
                        nodes.add(w);
                        w.visited = true;
                        w.parentNode = v;
                    }
                }

            //left
            if (v.column > 0)
                if (l[v.row][v.column - 1] != null) {
                    Node w = l[v.row][v.column - 1];
                    if (!w.visited) {
                        nodes.add(w);
                        w.visited = true;
                        w.parentNode = v;
                    }
                }

        }

        List<Node> result = new ArrayList<>();


        while (v.parentNode != null) {
            result.add(v);
            v = v.parentNode;
        }
        return result;
    }

    private class Node {

        private int row, column;
        boolean visited;
        Node parentNode;

        public int getRow() {
            return row;
        }

        public int getColumn() {

            return column;
        }

        public Node(int row, int column) {
            this.row = row;
            this.column = column;
            visited = false;
        }


    }
}