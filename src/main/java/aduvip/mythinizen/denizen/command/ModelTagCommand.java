package aduvip.mythinizen.denizen.command;

import aduvip.mythinizen.Utils.Utils;
import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizencore.exceptions.InvalidArgumentsException;
import com.denizenscript.denizencore.objects.Argument;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.scripts.ScriptEntry;
import com.denizenscript.denizencore.scripts.commands.AbstractCommand;
import com.denizenscript.denizencore.utilities.debugging.Debug;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.entity.BukkitEntity;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.bone.BoneBehaviorTypes;
import com.ticxo.modelengine.api.model.bone.type.NameTag;
import com.ticxo.modelengine.api.model.ModeledEntity;

import java.util.*;

public class ModelTagCommand extends AbstractCommand {
    public ModelTagCommand() {
        setName("modeltag");
        setSyntax("modeltag (<entity>) [<bone_tag>](:<string>)");
        setRequiredArguments(1, 2);
    }

    public void parseArgs(ScriptEntry scriptEntry) throws InvalidArgumentsException {
        for (ScriptEntry.ArgumentIterator argumentIterator = scriptEntry.iterator(); argumentIterator.hasNext(); ) {
            Argument arg = argumentIterator.next();
            if (!scriptEntry.hasObject("entity") && arg
                    .matchesArgumentType(EntityTag.class)) {
                scriptEntry.addObject("entity", arg.asType(EntityTag.class));
                continue;
            }
            if (arg.hasPrefix()) {
                scriptEntry.addObject("bone_id", new ElementTag(arg.getPrefix().getValue()));
                scriptEntry.addObject("string", arg.object);
                continue;
            }
            arg.reportUnhandled();
        }
        if (scriptEntry.hasObject("entity")) {
            if (!scriptEntry.hasObject("bone_id"))
                throw new InvalidArgumentsException("Must specify a valid Bone_ID");
        } else throw new InvalidArgumentsException("Must specify a valid Modeled Entity");
        if (!scriptEntry.hasObject("string")) {
            scriptEntry.addObject("string", "null");
        }
    }

    public void execute(ScriptEntry scriptEntry) {
        EntityTag entity = (EntityTag) scriptEntry.getObject("entity");
        String bone_id = scriptEntry.getObjectTag("bone_id").toString();
        String string = scriptEntry.getObjectTag("string").toString();
        if (Utils.isModeled(entity)) {
            ModeledEntity modeledEntity = ModelEngineAPI.getModeledEntity(entity.getUUID());
            Utils.Model_Tag(modeledEntity,bone_id,string);
        }
    }
}
