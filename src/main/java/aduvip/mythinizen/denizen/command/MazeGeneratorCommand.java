package aduvip.mythinizen.denizen.command;

import aduvip.mythinizen.Utils.Utils;
import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizencore.exceptions.InvalidArgumentsException;
import com.denizenscript.denizencore.objects.Argument;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.objects.core.ListTag;
import com.denizenscript.denizencore.objects.core.MapTag;
import com.denizenscript.denizencore.scripts.ScriptEntry;
import com.denizenscript.denizencore.scripts.commands.AbstractCommand;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ModeledEntity;
import maze.DFS;
import maze.MazeGenerator;
import maze.Prim;
import maze.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MazeGeneratorCommand extends AbstractCommand {
    public MazeGeneratorCommand() {
        setName("mazegenerator");
        setSyntax("mazegenerator (height:<height>) (width:<width>) (seed:<seed>) <algorithm>");
        setRequiredArguments(1, 4);
    }

    public void parseArgs(ScriptEntry scriptEntry) throws InvalidArgumentsException {
        for (ScriptEntry.ArgumentIterator argumentIterator = scriptEntry.iterator(); argumentIterator.hasNext(); ) {
            Argument arg = argumentIterator.next();
            if (!scriptEntry.hasObject("height") && arg
                    .matchesArgumentType(ElementTag.class)) {
                scriptEntry.addObject("height", arg.asElement());
                continue;
            }
            if (!scriptEntry.hasObject("width") && arg
                    .matchesArgumentType(ElementTag.class)) {
                scriptEntry.addObject("width", arg.asElement());
                continue;
            }
            if (!scriptEntry.hasObject("seed") && arg
                    .matchesArgumentType(ElementTag.class)) {
                scriptEntry.addObject("seed", arg.asElement());
                continue;
            }
            if (!scriptEntry.hasObject("algorithm") && arg
                    .matchesArgumentType(ElementTag.class)) {
                scriptEntry.addObject("algorithm", arg.asElement());
                continue;
            }
            arg.reportUnhandled();
        }
        if (!scriptEntry.hasObject("height")) throw new InvalidArgumentsException("Must specify a valid Maze Width");
        if (!scriptEntry.hasObject("width")) throw new InvalidArgumentsException("Must specify a valid Maze Height");
        if (!scriptEntry.hasObject("seed")) throw new InvalidArgumentsException("Must specify a valid Maze seed");
        if (!scriptEntry.hasObject("algorithm")) {
            scriptEntry.addObject("algorithm", "DFS");
        }
    }

    public void execute(ScriptEntry scriptEntry) {
        MazeGenerator maze = null;
        int height = Integer.parseInt(String.valueOf(scriptEntry.getObject("height")));
        int width = Integer.parseInt(String.valueOf(scriptEntry.getObject("width")));
        int seed = Integer.parseInt(String.valueOf(scriptEntry.getObject("seed")));
        String algorithm = scriptEntry.getObjectTag("algorithm").toString();
        if(Objects.equals(algorithm, "DFS")){
            maze = new DFS(height, width);
        }else if (Objects.equals(algorithm, "Prim")){
            maze = new Prim(height, width);
        }else{
            maze = new DFS(height, width);
        }
        List<List<String>> matrix = maze.toListString();

        MapTag result = new MapTag();
        ListTag cols = new ListTag();
        //add maze matrix
        for(List<String> col: matrix){
            ListTag rows = new ListTag();
            for(String row: col){
                rows.addObject(new ElementTag(row));
            }
            cols.addObject(rows);
        }
        result.putObject("matrix", cols);
        //add maze rooms
        List<ObjectTag> rooms = new ArrayList<>();
        for(Room room: maze.getRooms()){
            MapTag roomDetail = new MapTag();
            roomDetail.putObject("fromX", new ElementTag(room.getFromX()));
            roomDetail.putObject("fromY", new ElementTag(room.getFromY()));
            roomDetail.putObject("sizeX", new ElementTag(room.getSizeX()));
            roomDetail.putObject("sizeY", new ElementTag(room.getSizeY()));
            roomDetail.putObject("roomType", new ElementTag(room.getType()));
            rooms.add(roomDetail);
        }
        ListTag listRoom = new ListTag(rooms);
        result.putObject("rooms", listRoom);
        result.putObject("matrix", cols);
        scriptEntry.saveObject("generated_maze", result);
    }
}
