package maze.cell;

import java.awt.*;

public abstract class pathCell extends mazeCell{
    public Color baseColor;
    protected pathCell(int x, int y){
        super(x,y);
    }
}
