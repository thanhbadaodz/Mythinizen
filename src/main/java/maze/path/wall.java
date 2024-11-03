package maze.path;

import maze.cell.pathCell;

import java.awt.*;

public class wall extends pathCell {
    public wall(int x, int y){
        super(x, y);
        this.baseColor = Color.WHITE;
        this.c = this.baseColor;
        this.symbol = "w";
    }
}
