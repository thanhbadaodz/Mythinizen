package maze.cell;

import maze.room.Room;

public class roomCell extends mazeCell{
    public Room belongRoom;
    public roomCell(int x, int y, Room belongRoom){
        super(x, y);
        this.belongRoom = belongRoom;
        this.c = belongRoom.getC();
    }
}
