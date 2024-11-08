package aduvip.mythinizen;

import java.util.Objects;
import java.util.logging.Logger;

import aduvip.mythinizen.denizen.command.MazeGeneratorCommand;
import aduvip.mythinizen.denizen.command.ModelEnchantCommand;
import aduvip.mythinizen.denizen.command.ModelTagCommand;
import aduvip.mythinizen.denizen.tag.list.ModelEngineBaseProperties;
import aduvip.mythinizen.mythicmobs.conditions.flagContains;
import aduvip.mythinizen.mythicmobs.conditions.flagged;
import aduvip.mythinizen.mythicmobs.mechanics.customevent;
import aduvip.mythinizen.command.concac;
import aduvip.mythinizen.mythicmobs.mechanics.denizen;
import aduvip.mythinizen.mythicmobs.targets.Flagged;
import aduvip.mythinizen.mythicmobs.targets.tess;
import aduvip.mythinizen.denizen.command.ModelEngineCommand;
import aduvip.mythinizen.denizen.tag.entity.ModelEngineProperties;
import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizen.utilities.TextWidthHelper;
import com.denizenscript.denizencore.DenizenCore;
import com.denizenscript.denizencore.objects.core.ListTag;
import com.denizenscript.denizencore.objects.properties.PropertyParser;
import io.lumine.mythic.bukkit.events.MythicConditionLoadEvent;
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
        Objects.requireNonNull(getCommand("tt")).setExecutor(new concac(log));
        //Command Register
        DenizenCore.commandRegistry.registerCommand(MazeGeneratorCommand.class);
        DenizenCore.commandRegistry.registerCommand(ModelEngineCommand.class);
        DenizenCore.commandRegistry.registerCommand(ModelTagCommand.class);
        DenizenCore.commandRegistry.registerCommand(ModelEnchantCommand.class);
        //Properties Register
        PropertyParser.registerProperty(ModelEngineProperties.class, EntityTag.class);
        PropertyParser.registerProperty(ModelEngineBaseProperties.class, ListTag.class);
        //
        TextWidthHelper.setWidth(2, "ị");
        TextWidthHelper.setWidth(3, "íì");
        TextWidthHelper.setWidth(4, "IÍÌỊỈỉ");
        TextWidthHelper.setWidth(5, "Ĩĩ");
        TextWidthHelper.setWidth(7, "đĐ");
        TextWidthHelper.setWidth(8, "ơớờởỡưứừửữựƠỚỜỠỞƯỨỪỬỰỮᾯᾧῼῳῲῴῶῷ");
        TextWidthHelper.setWidth(14, "");
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
    @EventHandler
    public void onMythicConditionLoad(MythicConditionLoadEvent event)	{
        if(event.getConditionName().equalsIgnoreCase("flagged"))	{
            event.register(new flagged(event.getConfig()));
        }
        if(event.getConditionName().equalsIgnoreCase("flagContains"))	{
            event.register(new flagContains(event.getConfig()));
        }
    }

}