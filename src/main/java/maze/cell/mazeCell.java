package maze.cell;

import java.awt.*;

public class mazeCell {
    public Color baseColor;

    public String getSymbol() {
        return symbol;
    }

    protected String symbol;
    protected int x;
    protected int y;
    protected Color c;
    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x = x;
    }
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }
    protected mazeCell(int x, int y){
        this.setX(x);
        this.setY(y);
    }
    public Color getC(){
        return c;
    }
    public void render(Graphics g, int size, int offsetX, int offsetY){
        Color color = this.c;
        int bonusOffsetY = 40;
        int bonusOffsetX = 15;
        g.setColor(color);
        g.fillRect(
                offsetX + this.x * size + bonusOffsetX,
                offsetY + this.y * size + bonusOffsetY,
                size + 1,
                size + 1);
    }
}
