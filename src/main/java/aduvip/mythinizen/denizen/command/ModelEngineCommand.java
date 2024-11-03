package aduvip.mythinizen.denizen.command;

import aduvip.mythinizen.Utils.Utils;
import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizen.utilities.Utilities;
import com.denizenscript.denizencore.exceptions.InvalidArgumentsException;
import com.denizenscript.denizencore.objects.Argument;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.objects.core.ListTag;
import com.denizenscript.denizencore.scripts.ScriptEntry;
import com.denizenscript.denizencore.scripts.commands.AbstractCommand;
import com.denizenscript.denizencore.utilities.debugging.Debug;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.entity.BukkitEntity;
import com.ticxo.modelengine.api.generator.blueprint.ModelBlueprint;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.checkerframework.checker.guieffect.qual.UI;

import java.util.List;

public class ModelEngineCommand extends AbstractCommand {
    public ModelEngineCommand() {
        setName("modelengine");
        setSyntax("modelengine [set/unset] (<entity>) [<model_id>] (scale:<#.#>) (see_by:<entity>|...)");
        setRequiredArguments(2, 5);
    }

    enum Action {
        SET, UNSET;
    }
    public void addCustomTabCompletions(AbstractCommand.TabCompletionsBuilder tab) {
        tab.add(ModelEngineAPI.getAPI().getModelRegistry().getKeys());
        tab.add(Action.values());
    }

    public void parseArgs(ScriptEntry scriptEntry) throws InvalidArgumentsException {
        for (ScriptEntry.ArgumentIterator argumentIterator = scriptEntry.iterator(); argumentIterator.hasNext(); ) {
            Argument arg = argumentIterator.next();
            if (!scriptEntry.hasObject("action") && arg
                    .matchesEnum(Action.class)) {
                scriptEntry.addObject("action", arg.asElement());
                continue;
            }
            if (!scriptEntry.hasObject("entity") && arg
                    .matchesArgumentTypes(EntityTag.class)) {
                scriptEntry.addObject("entity", arg.asType(EntityTag.class));
                continue;
            }
            ModelBlueprint blueprint = ModelEngineAPI.getBlueprint(arg.toString().toLowerCase());
            if (!scriptEntry.hasObject("model_id") && blueprint != null) {
                scriptEntry.addObject("model_id", arg.asElement());
                continue;
            }
            if (!scriptEntry.hasObject("scale") && arg.matchesFloat()) {
                scriptEntry.addObject("scale", arg.asElement());
                continue;
            }
            if (!scriptEntry.hasObject("see_by") && arg
                    .matchesArgumentList(EntityTag.class)) {
                scriptEntry.addObject("see_by", ((ListTag) arg.asType(ListTag.class)).filter(EntityTag.class, scriptEntry));
                continue;
            }
            arg.reportUnhandled();
            Debug.log(arg.toString());
        }
        if (scriptEntry.getObjectTag("action")) {
            if (!scriptEntry.hasObject("entity"))
                throw new InvalidArgumentsException("Must specify a valid EntityTag");
            if (!scriptEntry.hasObject("model_id"))
                throw new InvalidArgumentsException("Must specify a valid Model Engine Model_ID");
            if (!scriptEntry.hasObject("scale"))
                scriptEntry.addObject("scale", new ElementTag("1.0"));
            if (!scriptEntry.hasObject("see_by"))
                scriptEntry.defaultObject("see_by", Utilities.entryDefaultEntityList(scriptEntry, true));
        } else throw new InvalidArgumentsException("Must specify a valid action!");
    }

    public void execute(ScriptEntry scriptEntry) {
        ElementTag action = scriptEntry.getElement("action");
        EntityTag entity = (EntityTag) scriptEntry.getObject("entity");
        List<EntityTag> see_by = (List<EntityTag>) scriptEntry.getObject("see_by");
        if (action.asLowerString().equalsIgnoreCase("set")){
            String model_id = scriptEntry.getObjectTag("model_id").toString();
            double scale = Double.parseDouble(scriptEntry.getElement("scale").toString());
            if (Utils.isModeled(entity)) {
                Utils.Unset_Model(ModelEngineAPI.getModeledEntity(entity.getBukkitEntity()));
            }
            Utils.Set_Model(entity,model_id,scale);
        }else if(action.asLowerString().equalsIgnoreCase("unset")){
            Utils.Unset_Model(ModelEngineAPI.getModeledEntity(entity.getBukkitEntity()));
        }
        Debug.log(see_by.toString());
    }
}
