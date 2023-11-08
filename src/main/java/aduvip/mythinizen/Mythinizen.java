package aduvip.mythinizen;

import java.util.Objects;
import java.util.logging.Logger;

import aduvip.mythinizen.denizen.tag.list.ModelEngineBaseProperties;
import aduvip.mythinizen.mythicmobs.mechanics.customevent;
import aduvip.mythinizen.command.tt;
import aduvip.mythinizen.mythicmobs.mechanics.denizen;
import aduvip.mythinizen.mythicmobs.targets.Flagged;
import aduvip.mythinizen.mythicmobs.targets.tess;
import aduvip.mythinizen.denizen.command.ModelEngineCommand;
import aduvip.mythinizen.denizen.tag.entity.ModelEngineProperties;
import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizencore.DenizenCore;
import com.denizenscript.denizencore.objects.core.ListTag;
import com.denizenscript.denizencore.objects.properties.PropertyParser;
import io.lumine.mythic.bukkit.events.MythicTargeterLoadEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;


public class Mythinizen extends JavaPlugin implements Listener {

    private Logger log;
    @Override
    public void onEnable() {
        log = this.getLogger();
        Bukkit.getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("tt")).setExecutor(new tt(log));
        //Command Register
        DenizenCore.commandRegistry.registerCommand(ModelEngineCommand.class);
        //Properties Register
        PropertyParser.registerProperty(ModelEngineProperties.class, EntityTag.class);
        PropertyParser.registerProperty(ModelEngineBaseProperties.class, ListTag.class);
        //
        log.info("Mythinizen Enabled");
    }

    public void onDisable(){
        log.info("Mythinizen Disabled!");
    }

    /*
     * Registers custom MythicMobs Events
     */
    @EventHandler
    public void onMythicMechanicLoad(MythicMechanicLoadEvent event) {
        if (event.getMechanicName().equalsIgnoreCase("customevent")) event.register(new customevent(event.getConfig()));
        if (event.getMechanicName().equalsIgnoreCase("denizen")) event.register(new denizen(event.getConfig()));
    }
    @EventHandler
    public void onMythicTargeterLoadEvent( MythicTargeterLoadEvent event) {
        if (event.getTargeterName().equalsIgnoreCase("flagged")) event.register(new Flagged(event.getContainer().getManager(),event.getConfig()));
        if (event.getTargeterName().equalsIgnoreCase("tess")) event.register(new tess(event.getContainer().getManager(),event.getConfig()));
    }

}