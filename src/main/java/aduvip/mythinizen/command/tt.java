package aduvip.mythinizen.command;
import com.denizenscript.denizencore.DenizenCore;
import com.denizenscript.denizencore.scripts.ScriptEntryData;
import com.denizenscript.denizencore.scripts.queues.ScriptQueue;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

import com.denizenscript.denizencore.events.core.CustomScriptEvent;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.objects.core.ListTag;
import com.denizenscript.denizencore.objects.core.MapTag;
import com.denizenscript.denizencore.scripts.ScriptEntry;
import com.denizenscript.denizencore.scripts.commands.AbstractCommand;
import com.denizenscript.denizencore.scripts.commands.generator.ArgDefaultNull;
import com.denizenscript.denizencore.scripts.commands.generator.ArgName;
import com.denizenscript.denizencore.scripts.commands.generator.ArgPrefixed;
import com.denizenscript.denizencore.utilities.CoreUtilities;
import com.denizenscript.denizencore.objects.core.ScriptTag;
public class tt implements CommandExecutor {
    private final Logger log;
    public tt(Logger logger) {
        this.log = logger;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Xử lý lệnh ở đây
        String id = null;
        if (args[0] != null) {
            id = args[0];
            log.info(id);
        } else
            log.info("Không Có args nào cả");
        //
        return true;
    }
}