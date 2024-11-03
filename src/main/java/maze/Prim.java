package maze;

import maze.cell.mazeCell;
import maze.path.path;
import maze.path.wall;
import maze.room.Room;
import maze.room.monsterRoom;
import maze.room.treasureRoom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Prim extends MazeGenerator {
    public Prim(int w, int h) {
        super(w, h);
    }
    @Override
    public void generateMaze() {

        for (int r = 0; r < this.getWidth(); r++){
            for(int c = 0; c < this.getHeight(); c++){
                maze[r][c] = new wall(r, c);
            }
        }
        this.tryAddRoom();
        while (true){
            int randX = rand.nextInt(this.getWidth() - 3) + 1;
            int randY = rand.nextInt(this.getHeight() - 3) + 1;
            if (availableForPath(randX, randY)){
                pathCreate(randX, randY);
                walls.add(maze[randX][randY]);
                break;
            }
        }
        while (!walls.isEmpty()) {
            // Chọn ngẫu nhiên một bức tường từ danh sách
            mazeCell wall = walls.remove(rand.nextInt(walls.size()));
            int x = wall.getX();
            int y = wall.getY();
            boolean b = countPathAround(x, y, 1) <= 2;
            if (canBeRemoved(x, y) && !nearRoom(wall) && countPathAround(x, y, 1) <= 2) {
                pathCreate(x, y);
                addWalls(x, y); // Thêm các bức tường mới từ ô này vào danh sách
            }
        }
        this.tryConnectRoomToPath();
        this.findDeadEnd();
        System.out.println("done");
    }
    // Kiểm tra nếu tường có thể phá vỡ (phải phân chia một ô đường và một ô chưa mở)
    private boolean canBeRemoved(int x, int y) {
        int count = 0;
        for(int dir: directions){
            int ax = x + dx[dir];
            int ay = y + dy[dir];
            int bx = x + dx[dir] * 2;
            int by = y + dy[dir] * 2;
            // Nếu một bên của tường là đường và bên kia là tường thì có thể phá vỡ
            if (this.exists(ax, ay)) {
                if (!((maze[ax][ay] instanceof  wall)))
                    return true;
            }
        }
        return false;
    }

    // Thêm các bức tường xung quanh một ô vào danh sách
    private void addWalls(int x, int y) {
        Integer[] d = shuffle(directions.clone());
        for(int dir: d){
            int ax = x + dx[dir];
            int ay = y + dy[dir];
            int bx = x + dx[dir] * 2;
            int by = y + dy[dir] * 2;
            if (this.exists(bx, by)){
                if( maze[ax][ay] instanceof wall && maze[bx][by] instanceof wall) {
                    walls.add(maze[ax][ay]);
                }
            }
        }
    }

    public static void main(String[] args) {
        Prim dfs = new Prim(45,45);
        dfs.getRaw();
    }
}

