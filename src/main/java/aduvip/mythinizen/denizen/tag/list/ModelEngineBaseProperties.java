package aduvip.mythinizen.denizen.tag.list;

import com.denizenscript.denizencore.objects.Mechanism;
import com.denizenscript.denizencore.objects.core.ListTag;
import com.denizenscript.denizencore.objects.properties.Property;
import com.denizenscript.denizencore.objects.properties.PropertyParser;
import com.ticxo.modelengine.api.ModelEngineAPI;


public class ModelEngineBaseProperties implements Property {
    public String getPropertyString() {
    return null;
    }

    public String getPropertyId() {
    return "Model Engine Model_IDs";
    }

    public void adjust(Mechanism mechanism) {}


    public static boolean describes(ListTag list) {
        return list != null;
    }
    public static ModelEngineBaseProperties getFrom(ListTag list) {
        if (!describes(list))
            return null;
        return new ModelEngineBaseProperties(list);
    }
    public ModelEngineBaseProperties(ListTag list) {
    }
    static ListTag list;


    public static void register() {
        PropertyParser.registerTag(ModelEngineBaseProperties.class, ListTag.class, "modelengine_model_ids", (attribute, object) -> new ListTag(object.list_ModelEngine_IDs()), "modelengine_model_ids");
   }

    public ListTag list_ModelEngine_IDs() {
        return new ListTag(ModelEngineAPI.getAPI().getModelRegistry().getKeys());
    }

}
