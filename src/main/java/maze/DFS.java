package maze;

import maze.cell.mazeCell;
import maze.path.path;
import maze.path.wall;
import maze.room.Room;
import maze.room.monsterRoom;
import maze.room.treasureRoom;

import java.util.Collections;
import java.util.List;

public class DFS extends MazeGenerator {
    public DFS(int w, int h) {
        super(w, h);
    }
    void GeneratePath(mazeCell c) {
        pathCreate(c.getX(), c.getY());
        Integer[] d = shuffle(directions.clone());
        for(int dir: d){
            int ax = c.getX() + dx[dir];
            int ay = c.getY() + dy[dir];
            int bx = c.getX() + dx[dir] * 2;
            int by = c.getY() + dy[dir] * 2;
            if (availableForPath(bx, by)){
                GeneratePath(maze[ax][ay]);

            }
        }
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
                GeneratePath(maze[randX][randY]);
                break;
            }
        }
        this.tryConnectRoomToPath();
        this.findDeadEnd();
        this.setStart();
//        System.out.println("done");
    }

    public static void main(String[] args) {
        DFS dfs = new DFS(100,100);
        dfs.getRaw();
    }
}

