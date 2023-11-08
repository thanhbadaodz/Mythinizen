package aduvip.mythinizen.denizen.tag.entity;

import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizencore.objects.Mechanism;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.objects.core.ListTag;
import com.denizenscript.denizencore.objects.properties.Property;
import com.denizenscript.denizencore.objects.properties.PropertyParser;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;

import java.util.Set;
import java.util.UUID;

import static com.denizenscript.denizencore.objects.core.ElementTag.tagProcessor;


public class ModelEngineProperties implements Property {
    public String getPropertyString() {
    return null;
    }

    public String getPropertyId() {
    return "ModeledEntity";
    }

    public void adjust(Mechanism mechanism) {}


    public static boolean describes(ObjectTag object) {
        return object instanceof EntityTag;
    }
    public static ModelEngineProperties getFrom(ObjectTag object) {
        if (!describes(object))
            return null;
        return new ModelEngineProperties((EntityTag)object);
    }
    public ModelEngineProperties(EntityTag entity) {
        this.entity = entity;
    }
    EntityTag entity;


    public static void register() {
        PropertyParser.registerTag(ModelEngineProperties.class, ElementTag.class, "is_modeled", (attribute, object) -> new ElementTag(object.isModeled()), "is_modeled");
        PropertyParser.registerTag(ModelEngineProperties.class, ListTag.class, "modeled_ids", (attribute, object) -> new ListTag(object.Modeled_IDs()), "modeled_ids");
    }

    public boolean isModeled() {
        return (ModelEngineAPI.getModeledEntity(this.entity.getBukkitEntity()) != null);
    }

    public ListTag Modeled_IDs() {
        ListTag result = new ListTag();
        ModeledEntity listActive = ModelEngineAPI.getModeledEntity(this.entity.getBukkitEntity());
        if (listActive != null){
            for (ActiveModel activeModel : listActive.getModels().values()) {
                result.add(activeModel.getBlueprint().getName());
            }
        }
        return result;
    }

}
