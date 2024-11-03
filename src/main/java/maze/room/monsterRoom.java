package maze.room;

import java.awt.*;
import maze.MazeGenerator;

public class monsterRoom extends Room{
    public monsterRoom(int fromX, int fromY, int sizeX, int sizeY, MazeGenerator maze){
        super(fromX, fromY, sizeX, sizeY, maze);
        this.c = Color.PINK;
        this.symbols = "mR";
        this.type = "monsterRoom";
    }
}
