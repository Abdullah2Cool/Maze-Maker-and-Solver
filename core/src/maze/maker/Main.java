package maze.maker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Random;

public class Main extends ApplicationAdapter {

    public static ShapeRenderer shape;
    public static int fWidth = 960, fHeight = 540;
    public static int scl;
    public static int rows, cols;
    public static Cell[][] Grid;
    public static int CalcPerFrame = 40;
    public static int FPS = 60;
    ArrayList<Cell> Stack = new ArrayList<Cell>();
    Cell current, next;
    Random rand;

    public static boolean bMazeFinished = false;

    Cell Start, Target;
    PathFinder path;

    @Override
    public void create() {
        shape = new ShapeRenderer();
        scl = 20; /////// INCREASE THIS NUMBER TO MAKE THE MAZE SMALLER
        rows = fWidth / scl;
        cols = fHeight / scl;
        Grid = createGrid();
        rand = new Random();
        current = getCell(0, cols - 1);

        Start = getCell(0, cols -1);
        Target = getCell(rows - 1, 0);

        path = new PathFinder(Start, Target);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(255 / 255f, 255 / 255f, 255 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                Grid[x][y].draw();
            }
        }

        if (!bMazeFinished) {
            current.drawMazeMaker();
            for (int i = 0; i < CalcPerFrame; i++) {
                current.bVisited = true;
                next = current.checkNeighbours();
                if (next != null) {
                    next.bVisited = true;

                    Stack.add(current);

                    removeWalls(current, next);

                    current = next;
                } else if (Stack.size() > 0) {
                    current = Stack.get(Stack.size() - 1);
                    Stack.remove(Stack.get(Stack.size() - 1));
                }
                if (Stack.size() == 0) {
                    bMazeFinished = true;
                    break;
                }
            }
        } else {
            path.FindPath(); /////// COMMENT THIS OUT IF YOU DON'T WANT TO SOLVE IT
        }
    }

    public void removeWalls(Cell current, Cell next) {
        int x, y;
        x = current.x - next.x;
        y = current.y - next.y;
        if (x == 1) {
            current.Sides[2] = false;
            next.Sides[3] = false;
        } else if (x == -1) {
            current.Sides[3] = false;
            next.Sides[2] = false;
        }
        if (y == 1) {
            current.Sides[1] = false;
            next.Sides[0] = false;
        } else if (y == -1) {
            current.Sides[0] = false;
            next.Sides[1] = false;
        }
    }

    public Cell[][] createGrid() {
        Cell[][] Grid = new Cell[rows][cols];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                Cell cell = new Cell(x, y);
                Grid[x][y] = cell;
                System.out.println("(" + x * scl + ", " + y * scl + ")");
            }
            System.out.println();
        }
        return Grid;
    }

    public static Cell getCell(int x, int y) {
        if (x < 0 || y < 0 || x > rows - 1 || y > cols - 1) {
            return null;
        }
        return Grid[x][y];
    }

    @Override
    public void dispose() {
        shape.dispose();
    }
}
