package aduvip.mythinizen.denizen.command;

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

import java.util.List;

public class ModelEngineCommand extends AbstractCommand {
    public ModelEngineCommand() {
        setName("modelengine");
        setSyntax("modelengine [set/unset] [<model_id>] [<entity>|...] (scale:<#.#>) (see_by:<entity>|...)");
        setRequiredArguments(2, 5);
    }

    enum Action {
        SET, UNSET;
    }
    public void addCustomTabCompletions(AbstractCommand.TabCompletionsBuilder tab) {
        tab.add(ModelEngineAPI.getAPI().getModelRegistry().getKeys());
    }

    public void parseArgs(ScriptEntry scriptEntry) throws InvalidArgumentsException {
        for (ScriptEntry.ArgumentIterator argumentIterator = scriptEntry.iterator(); argumentIterator.hasNext(); ) {
            Argument arg = argumentIterator.next();
            if (!scriptEntry.hasObject("action") && arg
                    .matchesEnum(Action.class)) {
                scriptEntry.addObject("action", arg.asElement());
                continue;
            }
            ModelBlueprint blueprint = ModelEngineAPI.getBlueprint(arg.toString().toLowerCase());
            if (!scriptEntry.hasObject("model_id") && blueprint != null) {
                scriptEntry.addObject("model_id", arg.asElement());
                continue;
            }
            if (!scriptEntry.hasObject("entities") && arg
                    .matchesArgumentList(EntityTag.class)) {
                scriptEntry.addObject("entities", ((ListTag) arg.asType(ListTag.class)).filter(EntityTag.class, scriptEntry));
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
        if (scriptEntry.hasObject("action")) {
            if (!scriptEntry.hasObject("model_id"))
                throw new InvalidArgumentsException("Must specify a valid Model Engine Model_ID");
            if (!scriptEntry.hasObject("entities"))
                throw new InvalidArgumentsException("Must specify a valid EntityTag");
            if (!scriptEntry.hasObject("scale"))
                scriptEntry.addObject("scale", new ElementTag("1.0"));
            if (!scriptEntry.hasObject("see_by"))
                scriptEntry.defaultObject("see_by", Utilities.entryDefaultEntityList(scriptEntry, true));
        } else throw new InvalidArgumentsException("Must specify a valid action!");
    }

    public void execute(ScriptEntry scriptEntry) {
        ElementTag action = scriptEntry.getElement("action");
        ElementTag model_id = scriptEntry.getObjectTag("model_id");
        List<EntityTag> entities = (List<EntityTag>) scriptEntry.getObject("entities");
        ElementTag scale = scriptEntry.getElement("scale");
        List<EntityTag> see_by = (List<EntityTag>) scriptEntry.getObject("see_by");
        switch (action.asLowerString()) {
            case "set":
                for (EntityTag entity : entities) {
                    BukkitEntity base = new BukkitEntity(entity.getBukkitEntity());
                    ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(base);
                    ActiveModel activeModel = ModelEngineAPI.createActiveModel(model_id.toString());
                    activeModel.setScale(Double.parseDouble(scale.toString()));
                    activeModel.setHitboxScale(Double.parseDouble(scale.toString()));
                    modeledEntity.setSaved(true);
                    modeledEntity.setBaseEntityVisible(false);
                    modeledEntity.addModel(activeModel, true);

                }
                break;
            case "unset":
                for (EntityTag entity : entities) {
                    ModeledEntity modeledEntity = ModelEngineAPI.getModeledEntity(entity.getUUID());
                    for (ActiveModel activeModel : modeledEntity.getModels().values()) {
                        modeledEntity.removeModel(activeModel.getBlueprint().getName()).ifPresent(ActiveModel::destroy);
                    }
                }
        }
        Debug.log(action.toString());
        Debug.log(model_id.toString());
        Debug.log(entities.toString());
        Debug.log(scale.toString());
        Debug.log(see_by.toString());
    }
}
