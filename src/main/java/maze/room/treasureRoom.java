package maze.room;

import maze.MazeGenerator;

import java.awt.*;

public class treasureRoom extends Room{
    public treasureRoom(int fromX, int fromY, int sizeX, int sizeY, MazeGenerator maze){
        super(fromX, fromY, sizeX, sizeY, maze);
        this.c = Color.YELLOW;
        this.symbols = "tR";
        this.type = "treasureRoom";
    }
}
