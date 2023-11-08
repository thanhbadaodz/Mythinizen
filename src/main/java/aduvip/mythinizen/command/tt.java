package aduvip.mythinizen.command;
import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizen.objects.LocationTag;
import com.denizenscript.denizen.objects.PlayerTag;
import com.denizenscript.denizencore.utilities.debugging.Debug;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.entity.BukkitEntity;
import com.ticxo.modelengine.api.generator.blueprint.ModelBlueprint;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import io.lumine.mythic.api.adapters.AbstractLocation;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.spawning.spawners.MythicSpawner;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Logger;
public class tt implements CommandExecutor {
    private final Logger log;
    public tt(Logger logger) {
        this.log = logger;
    }
    public static MythicSpawner getMythicSpawner(String name) {
        return MythicBukkit.inst().getSpawnerManager().getSpawnerByName(name);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player= (Player)sender;
        if (args[0] != null) {
            String spawner_id = args[0];
            MythicSpawner spawner = getMythicSpawner(spawner_id);
            if(spawner != null) {
                AbstractLocation l = spawner.getLocation();
                LocationTag f_loc = new LocationTag((io.lumine.mythic.bukkit.BukkitAdapter.adapt(l.getWorld())),l.getX(),l.getY(),l.getZ());
                log.info("loc: %s".formatted(f_loc.toString()));
            }
        } else
            log.info("Không Có args nào cả");
        return true;
    }
}