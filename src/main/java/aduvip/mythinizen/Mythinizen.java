package aduvip.mythinizen;

import java.io.File;
import java.util.logging.Logger;

import aduvip.mythinizen.mythicmobs.mechanics.customevent;
import aduvip.mythinizen.command.tt;
import aduvip.mythinizen.mythicmobs.mechanics.denizen;
import aduvip.mythinizen.mythicmobs.targets.Flagged;
import aduvip.mythinizen.mythicmobs.targets.tess;
import io.lumine.mythic.bukkit.events.MythicTargeterLoadEvent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;

public class Mythinizen extends JavaPlugin implements Listener {

    private Logger log;

    @Override
    public void onEnable() {
        log = this.getLogger();
        Bukkit.getPluginManager().registerEvents(this, this);
        getCommand("tt").setExecutor(new tt(log));
        log.info("Mythinizen Enabled");
    }

    public void onDisable(){
        log.info("Mythinizen Disabled!");
    }

    /*
     * Registers all of the custom MythicMobs Events
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