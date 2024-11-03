package maze.room;

import maze.MazeGenerator;
import maze.cell.mazeCell;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Room {
    public String getSymbols() {
        return symbols;
    }

    protected String symbols;
    int fromX;
    int fromY;
    int sizeX;
    int sizeY;

    public String getType() {
        return type;
    }

    String type;
    MazeGenerator maze;
    public Room(int fromX, int fromY, int sizeX, int sizeY, MazeGenerator maze){
        this.fromX = fromX;
        this.fromY = fromY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.maze = maze;
    }
    protected Color c;
    public int getFromX(){
        return fromX;
    }
    public int getFromY(){
        return fromY;
    }
    public int getSizeX(){
        return sizeX;
    }
    public int getSizeY(){
        return sizeY;
    }
    public Color getC(){
        return c;
    }
    public List<mazeCell> calWalls(){
        List<mazeCell> walls = new ArrayList<>();
        for (int x = fromX; x < fromX + sizeX; x++){
            for (int y = fromY; y < fromY + sizeY; y++){
                if (maze.exists(x - 1, y - 1)) {
                    walls.add(maze.getMaze()[x][y]);
                }
            }
        }
        return walls;
    }
    public List<mazeCell> getCells(){
        List<mazeCell> cells = new ArrayList<>();
        for (int x = fromX; x < fromX + sizeX; x++){
            for (int y = fromY; y < fromY + sizeY; y++){
                if (maze.exists(x - 1, y - 1)){
                    cells.add(maze.getMaze()[x][y]);
                }
            }
        }
        return cells;
    }
}
