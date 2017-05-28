package devchallenge.labyrinth;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import devchallenge.labyrinth.models.Border;
import devchallenge.labyrinth.models.Cell;

public class LabyrinthGenerator {


    private int rowCount, columnCount;
    private int width, height;

    public LabyrinthGenerator(int rowCount, int columnCount, int width, int height) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.width = width;
        this.height = height;
    }



    public Cell[][] generateMaze() {
        Cell[][] maze = new Cell[rowCount][columnCount];
        // Initialize
        for (int i = 0; i < rowCount; i++)
            for (int j = 0; j < columnCount; j++)
                maze[i][j] = new Border(i,j,width,height);

        Random rand = new Random();
        // r for row、c for column
        // Generate random r
        int r = rand.nextInt(rowCount);
        while (r % 2 == 0) {
            r = rand.nextInt(rowCount);
        }
        // Generate random c
        int c = rand.nextInt(columnCount);
        while (c % 2 == 0) {
            c = rand.nextInt(columnCount);
        }
        // Starting cell
       // maze[r][c] = 0;

        //　Allocate the maze with recursive method
        recursion(r, c, maze);

        return maze;
    }

    public void recursion(int r, int c, Cell[][] maze) {
        // 4 random directions
        Integer[] randDirs = generateRandomDirections();
        // Examine each direction
        for (int i = 0; i < randDirs.length; i++) {

            switch (randDirs[i]) {
                case 1: // Up
                    //　Whether 2 cells up is out or not
                    if (r - 2 <= 0)
                        continue;
                    if (maze[r - 2][c] != null) {
                        maze[r - 2][c] = null;
                        maze[r - 1][c] = null;
                        recursion(r - 2, c, maze);
                    }
                    break;
                case 2: // Right
                    // Whether 2 cells to the right is out or not
                    if (c + 2 >= columnCount - 1)
                        continue;
                    if (maze[r][c + 2] != null) {
                        maze[r][c + 2] = null;
                        maze[r][c + 1] = null;
                        recursion(r, c + 2, maze);
                    }
                    break;
                case 3: // Down
                    // Whether 2 cells down is out or not
                    if (r + 2 >= rowCount - 1)
                        continue;
                    if (maze[r + 2][c] != null) {
                        maze[r + 2][c] = null;
                        maze[r + 1][c] = null;
                        recursion(r + 2, c, maze);
                    }
                    break;
                case 4: // Left
                    // Whether 2 cells to the left is out or not
                    if (c - 2 <= 0)
                        continue;
                    if (maze[r][c - 2] != null) {
                        maze[r][c - 2] = null;
                        maze[r][c - 1] = null;
                        recursion(r, c - 2, maze);
                    }
                    break;
            }
        }

    }

    /**
     * Generate an array with random directions 1-4
     *
     * @return Array containing 4 directions in random order
     */
    public Integer[] generateRandomDirections() {
        ArrayList<Integer> randoms = new ArrayList<Integer>();
        for (int i = 0; i < 4; i++)
            randoms.add(i + 1);
        Collections.shuffle(randoms);

        return randoms.toArray(new Integer[4]);
    }
 /*   // solve the maze using depth-first search
    private void solve(int x, int y) {
        if (x == 0 || y == 0 || x == n + 1 || y == n + 1) return;
        if (done || visited[x][y]) return;
        visited[x][y] = true;

        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
        StdDraw.show();
        StdDraw.pause(30);

        // reached middle
        if (x == n / 2 && y == n / 2) done = true;

        if (!north[x][y]) solve(x, y + 1);
        if (!east[x][y]) solve(x + 1, y);
        if (!south[x][y]) solve(x, y - 1);
        if (!west[x][y]) solve(x - 1, y);

        if (done) return;

        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
        StdDraw.show();
        StdDraw.pause(30);
    }

    // solve the maze starting from the start state
    public void solve() {
        for (int x = 1; x <= n; x++)
            for (int y = 1; y <= n; y++)
                visited[x][y] = false;
        done = false;
        solve(1, 1);
    }

*/
}