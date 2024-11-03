package maze.path;

import maze.cell.pathCell;

import java.awt.*;

public class path extends pathCell {
    public path(int x, int y){
        super(x, y);
        this.baseColor = Color.GRAY;
        this.c = this.baseColor;
        this.symbol = "p";
    }
}
