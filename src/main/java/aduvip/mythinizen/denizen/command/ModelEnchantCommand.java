package aduvip.mythinizen.denizen.command;

import aduvip.mythinizen.Utils.Utils;
import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizencore.exceptions.InvalidArgumentsException;
import com.denizenscript.denizencore.objects.Argument;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.scripts.ScriptEntry;
import com.denizenscript.denizencore.scripts.commands.AbstractCommand;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ModeledEntity;

public class ModelEnchantCommand extends AbstractCommand {
    public ModelEnchantCommand() {
        setName("modelenchant");
        setSyntax("modelenchant (<entity>) [<bone_id>] [<set_enchant>]");
        setRequiredArguments(1, 3);
    }

    enum Action {
        TRUE, FALSE, TOGGLE;
    }
    public void addCustomTabCompletions(AbstractCommand.TabCompletionsBuilder tab) {
        tab.add(Action.values());
    }
    public void parseArgs(ScriptEntry scriptEntry) throws InvalidArgumentsException {
        for (ScriptEntry.ArgumentIterator argumentIterator = scriptEntry.iterator(); argumentIterator.hasNext(); ) {
            Argument arg = argumentIterator.next();
            if (!scriptEntry.hasObject("entity") && arg
                    .matchesArgumentType(EntityTag.class)) {
                scriptEntry.addObject("entity", arg.asType(EntityTag.class));
                continue;
            }
            if (!scriptEntry.hasObject("bone_id")) {
                scriptEntry.addObject("bone_id", arg.asElement());
                continue;
            }
            if (!scriptEntry.hasObject("set_enchant")) {
                scriptEntry.addObject("set_enchant", arg.asElement());
                continue;
            }
            arg.reportUnhandled();
        }
        if (!scriptEntry.hasObject("entity")) {
            throw new InvalidArgumentsException("Must specify a valid Modeled Entity");
        }
        if (!scriptEntry.hasObject("bone_id")) {
            scriptEntry.addObject("bone_id",new ElementTag("*"));
        }
        if (!scriptEntry.hasObject("set_enchant")) {
            scriptEntry.addObject("set_enchant",new ElementTag("toggle"));
        }
    }

    public void execute(ScriptEntry scriptEntry) {
        EntityTag entity = (EntityTag) scriptEntry.getObject("entity");
        String bone_id = scriptEntry.getObject("bone_id").toString().toLowerCase();
        String set_enchant = scriptEntry.getObject("set_enchant").toString().toLowerCase();
        if (Utils.isModeled(entity)) {
            ModeledEntity modeledEntity = ModelEngineAPI.getModeledEntity(entity.getBukkitEntity());
            Utils.Model_Enchant(modeledEntity,bone_id,set_enchant);
        }
    }
}
