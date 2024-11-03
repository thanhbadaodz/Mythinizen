package maze.path;

import maze.cell.pathCell;

import java.awt.*;

public class end extends pathCell {
    public end(int x, int y){
        super(x, y);
        this.baseColor = Color.RED;
        this.c = this.baseColor;
        this.symbol = "e";
    }
}
