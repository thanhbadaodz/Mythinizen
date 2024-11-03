package maze;

import aduvip.mythinizen.Utils.Utils;
import maze.cell.mazeCell;
import maze.cell.pathCell;
import maze.cell.roomCell;
import maze.path.path;
import maze.path.end;
import maze.path.start;
import maze.path.wall;
import maze.room.Room;
import maze.room.monsterRoom;
import maze.room.treasureRoom;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

public abstract class MazeGenerator {
    Logger log = Logger.getLogger(MazeGenerator.class.getName());

    private static final int CELL_SIZE = 3;
    private int width;
    private int height;
    Random rand = new Random();

    protected final List<mazeCell> paths = new ArrayList<>();
    protected final List<mazeCell> walls = new ArrayList<>();
    protected final List<mazeCell> ends = new ArrayList<>();

    public List<Room> getRooms() {
        return rooms;
    }

    protected final List<Room> rooms = new ArrayList<>();


    protected mazeCell[][] maze;
    public mazeCell[][] getMaze() {
        return maze;
    }

    protected final Integer[] directions = {0, 1, 2 , 3};
    protected final int[] dx = {-1, 0 , 1 , 0};
    protected final int[] dy = {0, 1 , 0 , -1};
    public  MazeGenerator(int w, int h) {
        this.setWidth(w);
        this.setHeight(h);
        this.reset();
        generateMaze();
    }

    private void setHeight(int h) {
        this.height = h;
    }
    public int getHeight() {
        return this.height;
    }
    private void setWidth(int w) {
        this.width = w;
    }
    public int getWidth() {
        return this.width;
    }

    private void reset() {
        this.maze =  new mazeCell[this.getWidth()][this.getHeight()];
    }
    private void drawMaze(Graphics g) {
        for(int r = 0; r < this.width - 1; r++){
            for(int c = 0; c < this.height - 1; c++){
                try{
                    maze[r][c].render(g, CELL_SIZE, r, c);
                } catch (Exception ignored) {
                }
            }
        }
    }

    abstract void generateMaze();

    Integer[] shuffle(Integer[] array) {
        Integer[] result = array.clone();
        Collections.shuffle(Arrays.asList(result), rand);
        return result;
    }


    boolean nearRoom(mazeCell cell) {
        if (cell instanceof  roomCell) return false;
        List<mazeCell> scanned = scan(cell, 2);
        int count = 0;
        for(mazeCell c : scanned){
            if(!(c instanceof  roomCell)){
                count++;
            }
        }
        return count >= 2 && count < 4;
    }

    boolean availableForPath(int x,int y) {
        if(!exists(x, y)) return false;
        List<mazeCell> scanned = scan(maze[x][y], 1);
        for(mazeCell c : scanned){
            if(!(c instanceof  wall)){
                return false;
            }
        }
        return true;
    }

    void addRoom(Room room) {
        for(mazeCell c: room.getCells()){
            roomCell nRoom = new roomCell(c.getX(), c.getY(), room);
            maze[c.getX()][c.getY()] = nRoom;
        }
        rooms.add(room);
    }

    boolean availableForRoom(Room room) {
        int scanRange = 5;
        List<mazeCell> listCell = new ArrayList<>();
        for(mazeCell cell: room.calWalls()){
            List<mazeCell> scanned = scan(maze[cell.getX()][cell.getY()], scanRange);
            for(mazeCell c: scanned) {
                if (!listCell.contains(c)) listCell.add(c);
            }
        }
        for(mazeCell c: listCell){
            if (!(c instanceof wall)) return false;
        }
        return true;
    }
    private  List<mazeCell> scan(mazeCell cell, int range){
        List<mazeCell> result = new ArrayList<>();
        for(int i = cell.getX() - range; i <= cell.getX() + range; i++){
            for(int k = cell.getY() - range; k <= cell.getY() + range; k++){
                if((i == cell.getX() && k == cell.getY())|| !exists(i, k )) continue;
                try{
                    result.add(maze[i][k]);
                }catch (Exception ignored){

                }
            }
        }
        return result;
    }
    void pathCreate(int x, int y) {
        if(exists(x, y)){
            path p = new path(x, y);
            maze[x][y] = p;
            this.paths.add(p);
        }
    }
    void endCreate(int x, int y) {
        if(exists(x, y)){
            end e = new end(x, y);
            maze[x][y] = e;
            this.ends.add(e);
        }
    }
    public int countPathAround(int x, int y , int range) {
        int result = 0;
        if(!exists(x, y)) return -1;
        for(mazeCell c: scan(maze[x][y], range)){
            if(c instanceof path) result++;
        }
        return result;
    }
    public boolean exists(int x, int y) {
        return x >= 0 && x < height && y >= 0 && y < width;
    }
    public boolean isDeadEnd(mazeCell c) {
        int count = 0;
        for (int dir: directions) {
            int ax = c.getX() + dx[dir];
            int ay = c.getY() + dy[dir];
            if(!exists(ax, ay)) continue;
            if (maze[ax][ay] instanceof path) count++;
        }
        return count == 1;
    }
    void getRaw(){
        for (int y = -1; y < this.getHeight() ; y++){
            System.out.print(Utils.intForm(y , 3));
            for (int x = 0; x < this.getWidth() ; x++){
                if (y == -1) System.out.print(Utils.intForm(x , 3));
                else{
                    if (maze[x][y] instanceof wall){
                            System.out.print("███");
                    }else if (maze[x][y] instanceof end) {
                        System.out.print(" E ");
                    }
                    else{
                        System.out.print("   ");
                    }
                }
            }
            System.out.print("\n");
        }
    }
    void tryAddRoom(){
        int minRoomSize = 3;
        int maxRoomSize = 7;
        double arvRoomSize = (double) ((minRoomSize + maxRoomSize) / 2 * (minRoomSize + maxRoomSize)) / 2;
        double fillRate = 0.3;
        int roomAmount = (int)Math.ceil((this.getWidth() * this.getHeight() / arvRoomSize) * fillRate);
        int count = 0;
        int safe = 0;
        while (count < roomAmount && safe++ < 1000){
            int roomWidth = rand.nextInt(maxRoomSize - minRoomSize) + minRoomSize;
            int roomHeight = rand.nextInt(maxRoomSize - minRoomSize) + minRoomSize;
            int randX = rand.nextInt(this.getWidth() - roomWidth);
            int randY = rand.nextInt(this.getHeight() - roomHeight);
            Room[] chooseRoom = {
                    new monsterRoom(randX, randY, roomWidth, roomHeight,  this),
                    new treasureRoom(randX, randY, roomWidth, roomHeight, this)
                };
                Room pickRoom = chooseRoom[rand.nextInt(chooseRoom.length)];
            if (availableForRoom(pickRoom)){
                count++;
                addRoom(pickRoom);
            }
        }
    }
    void tryConnectRoomToPath(){
        loopRoom: for (Room r: rooms){
            List<mazeCell> calWalls = r.calWalls();
            Collections.shuffle(calWalls, rand);
            for(mazeCell c: calWalls){
                int x = c.getX();
                int y = c.getY();
                for (int i = 0; i < 4; i++){
                    int dir = directions[i];
                    int ax = x + dx[dir];
                    int ay = y + dy[dir];
                    int bx = x + dx[dir] * 2;
                    int by = y + dy[dir] * 2;
                    int cx = x + dx[dir] * 3;
                    int cy = y + dy[dir] * 3;
                    if (!exists(ax, ay) ||!exists(bx, by) ||!exists(cx, cy)) continue;
                    if (maze[ax][ay] instanceof wall && maze[bx][by] instanceof path){
                        pathCreate(ax, ay);
                        continue loopRoom;
                    }else if (maze[ax][ay] instanceof wall && maze[bx][by] instanceof wall && maze[cx][cy] instanceof path){
                        pathCreate(ax, ay);
                        pathCreate(bx, by);
                        continue loopRoom;
                    }
                }
            }
        }
    }
    void findDeadEnd(){
        for(mazeCell p: this.paths){
            if(isDeadEnd(p)){
                endCreate(p.getX(), p.getY());
            }
        }
    }
    void setStart(){
        mazeCell randEnd = this.ends.get(rand.nextInt(this.ends.size()));
        maze[randEnd.getX()][randEnd.getY()] = new start(randEnd.getX(), randEnd.getY());
    }
    public List<List<String>> toListString(){
        List<List<String>> cols = new ArrayList<>();
        for (int y = 0; y < this.getHeight() ; y++){
            List<String> rows = new ArrayList<>();
            for (int x = 0; x < this.getWidth(); x++){
                if (maze[x][y] instanceof pathCell){
                    rows.add(String.valueOf(maze[x][y].getSymbol()));
                }else if (maze[x][y] instanceof roomCell) {
                    rows.add(String.valueOf(((roomCell) maze[x][y]).belongRoom.getSymbols()));
                }
            }
            cols.add(rows);
        }
        return cols;
    }
    public static void main(String[] args) {
        DFS maze = new DFS(10,10);
        System.out.println(maze.toListString());
        maze.getRaw();
    }
}


