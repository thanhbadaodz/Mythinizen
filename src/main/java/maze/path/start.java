package maze.path;

import maze.cell.pathCell;

import java.awt.*;

public class start extends pathCell {
    public start(int x, int y){
        super(x, y);
        this.baseColor = Color.GREEN;
        this.c = this.baseColor;
        this.symbol = "s";
    }
}
