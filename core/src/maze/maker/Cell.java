/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze.maker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

import static maze.maker.Main.bMazeFinished;
import static maze.maker.Main.getCell;
import static maze.maker.Main.scl;

/**
 * @author hafiz
 */
public class Cell {

    ShapeRenderer shape;
    int x, y;
    float fDx, fDy, fWidth;
    boolean[] Sides;
    boolean bVisited;
    float nH, nG, nF;
    Cell ParentCell;
    boolean bHighlight = false;
    boolean bChecked = false;
    boolean bStart = false;
    boolean bTarget = false;

    public Cell(int x, int y) {
        shape = Main.shape;
        this.x = x;
        this.y = y;
        fDx = x * scl;
        fDy = y * scl;
        fWidth = 0.5f;
        bVisited = false;

        Sides = new boolean[4];
        Sides[0] = true; // top
        Sides[1] = true; // bottom
        Sides[2] = true; // left
        Sides[3] = true; // right

        nH = 0;
        nG = 0;
        ParentCell = null;
    }

    public void draw() {
        if (bVisited) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(Color.GRAY);
            shape.rect(fDx, fDy, scl, scl);
            shape.end();
        }
        if (bMazeFinished) {
            if (bStart) {
                shape.begin(ShapeRenderer.ShapeType.Filled);
                shape.setColor(Color.BLUE);
                shape.rect(fDx, fDy, scl, scl);
                shape.end();
            } else if (bTarget) {
                shape.begin(ShapeRenderer.ShapeType.Filled);
                shape.setColor(Color.RED);
                shape.rect(fDx, fDy, scl, scl);
                shape.end();
            } else if (bHighlight) {
                shape.begin(ShapeRenderer.ShapeType.Filled);
                shape.setColor(Color.WHITE);
                shape.rect(fDx, fDy, scl, scl);
//                shape.triangle(fDx, fDy, fDx, fDy + scl, fDx + scl, fDy + scl / 2);
//                shape.circle(fDx + scl/2, fDy + scl/2, scl/2);
                shape.end();
            } else if (bChecked) {
                shape.begin(ShapeRenderer.ShapeType.Filled);
                shape.setColor(Color.BROWN);
                shape.rect(fDx, fDy, scl, scl);
                shape.end();
            }
        }
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.BLACK);
        if (Sides[0]) {// top
            shape.rectLine(fDx, fDy + scl - fWidth, fDx + scl, fDy + scl + fWidth, fWidth * 2);
        }
        if (Sides[1]) {// bottom
            shape.rectLine(fDx, fDy - fWidth, fDx + scl, fDy + fWidth, fWidth * 2);
//            shape.line(0, 0, w, h);
        }
        if (Sides[2]) {// left
            shape.rectLine(fDx - fWidth, fDy, fDx + fWidth, fDy + scl, fWidth * 2);
        }
        if (Sides[3]) {// right
            shape.rectLine(fDx + scl - fWidth, fDy, fDx + scl + fWidth, fDy + scl, fWidth * 2);
        }
        shape.end();
    }

    public void drawMazeMaker() {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.GREEN);
        shape.rect(fDx, fDy, scl, scl);
        shape.end();
    }

    public Cell checkNeighbours() {
        ArrayList<Cell> Neighbours = new ArrayList<Cell>();
        Cell Top, Right, Bottom, Left;

        Top = getCell(x, y + 1);
        Bottom = getCell(x, y - 1);
        Left = getCell(x - 1, y);
        Right = getCell(x + 1, y);

        if (Top != null && !Top.bVisited) {
            Neighbours.add(Top);
        }
        if (Bottom != null && !Bottom.bVisited) {
            Neighbours.add(Bottom);
        }
        if (Left != null && !Left.bVisited) {
            Neighbours.add(Left);
        }
        if (Right != null && !Right.bVisited) {
            Neighbours.add(Right);
        }

        if (Neighbours.size() > 0) {
            int random = (int) (Math.random() * Neighbours.size());
            return Neighbours.get(random);
        } else {
            return null;
        }

//        for (Cell cell : Neighbours) {
//            cell.bVisited = true;
//        }

//        System.out.println(Top.x + ", " + Top.y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


    public int getnF() {
        return (int) nF;
    }

    public void setParentCell(Cell cell) {
        ParentCell = cell;
    }

    public Cell getParentCell() {
        return ParentCell;
    }
}
