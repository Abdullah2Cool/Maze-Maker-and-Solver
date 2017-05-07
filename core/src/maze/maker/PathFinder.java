package maze.maker;

import java.util.ArrayList;
import java.util.Collections;

import static maze.maker.Main.CalcPerFrame;
import static maze.maker.Main.cols;
import static maze.maker.Main.getCell;
import static maze.maker.Main.rows;

/**
 * Created by hafiz on 5/7/2017.
 */

public class PathFinder {
    private Cell TargetCell, StartCell;
    private ArrayList<Cell> OpenList = new ArrayList<Cell>();
    private ArrayList<Cell> ClosedList = new ArrayList<Cell>();
    private boolean bPathFound = false;
    private int nPerpendicular;
    Cell TempPathCell;

    public PathFinder(Cell TargetCell, Cell StartCell) {
        this.TargetCell = TargetCell;
        this.StartCell = StartCell;
        TargetCell.bTarget = true;
        StartCell.bStart = true;
        Calc_Heuristic();
        OpenList.add(StartCell);
        nPerpendicular = 10;
    }

    public void FindPath() {
        if (!bPathFound) {
            for (int i = 0; i < CalcPerFrame; i++) {
                Cell TempCell;
                Collections.sort(OpenList, new DistanceComparator());
                if (OpenList.size() > 0) {
                    TempCell = OpenList.get(0);
                    ClosedList.add(TempCell);
                    OpenList.remove(TempCell);
                    SetChildren(ClosedList.get(ClosedList.size() - 1));
                }
            }
        } else {
            HighlightPath();
        }
    }

    public void HighlightPath() {
        for (int i = 0; i < 10; i++) {
            if (TempPathCell != StartCell) {
                TempPathCell.bHighlight = true;
                TempPathCell.getParentCell().bHighlight = true;
                TempPathCell = TempPathCell.getParentCell();
//                System.out.println("Highlighting");
            } else {
                break;
            }
        }
    }


    public void Calc_Heuristic() {
        Cell TempCell;
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                TempCell = getCell(x, y);
                TempCell.nH = (int) (Math.abs(TempCell.getX() - TargetCell.getX()) +
                                        Math.abs(TempCell.getY() - TargetCell.getY()));
            }
        }
    }


    public void SetChildren(Cell ParentCell) {
        int x = (int) (ParentCell.getX());
        int y = (int) (ParentCell.getY());
        ClosedList.add(ParentCell);
        OpenList.remove(ParentCell);
        Cell[] ChildCells = new Cell[4];

        ChildCells[0] = getCell(x, y + 1); // top
        ChildCells[1] = getCell(x, y - 1); // bottom
        ChildCells[2] = getCell(x - 1, y); // left
        ChildCells[3] = getCell(x + 1, y); // right


        for (int i = 0; i < ChildCells.length; i++) {
            if (ChildCells[i] != null) {
                if (ChildCells[i] == TargetCell) {
//                Path.add(ChildCells[i]);
                    TempPathCell = ParentCell;
                    bPathFound = true;
                }
                if (!CheckWalls(ParentCell, ChildCells[i])) {
                    if (ChildCells[i].ParentCell == null) {
                        ChildCells[i].setParentCell(ParentCell);
                        OpenList.add(ChildCells[i]);
                        ChildCells[i].bChecked = true;
                        ChildCells[i].nG = ParentCell.nG + nPerpendicular;
                    } else if (OpenList.contains(ChildCells[i])) {
                        if (ParentCell.nG + nPerpendicular < ChildCells[i].nG) {
                            ChildCells[i].setParentCell(ParentCell);
                            ChildCells[i].nG = ParentCell.nG + nPerpendicular;
                        }
                    }
                }
            }
        }
    }

    boolean CheckWalls(Cell Parent, Cell Child) {
        if (Parent.getX() == Child.getX()) {
            if (Parent.getY() < Child.getY()) { // top
                if (Parent.Sides[0] || Child.Sides[1]) {
                    return true;
                }
            } else if (Parent.getY() > Child.getY()) { // bottom
                if (Parent.Sides[1] || Child.Sides[0]) {
                    return true;
                }
            }
        }
        if (Parent.getY() == Child.getY()) {
            if (Parent.getX() < Child.getX()) { // left
                if (Parent.Sides[3] || Child.Sides[2]) {
                    return true;
                }
            } else if (Parent.getX() > Child.getX()) { // right
                if (Parent.Sides[2] || Child.Sides[3]) {
                    return true;
                }
            }
        }
        return false;
    }
}
