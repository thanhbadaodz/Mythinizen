package aduvip.mythinizen.Utils;

import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizencore.objects.core.ListTag;
import com.denizenscript.denizencore.utilities.debugging.Debug;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import com.ticxo.modelengine.api.model.bone.BoneBehaviorTypes;
import com.ticxo.modelengine.api.model.bone.ModelBone;
import org.bukkit.ChatColor;

import java.util.Collection;


public class Utils {
    public static Boolean isModeled(EntityTag entity) {
        return ModelEngineAPI.getModeledEntity(entity.getBukkitEntity()) != null;
    }

    public static ListTag Modeled_IDs(EntityTag entity) {
        ListTag result = new ListTag();
        ModeledEntity listActive = ModelEngineAPI.getModeledEntity(entity.getBukkitEntity());
        if (listActive != null) {
            for (ActiveModel activeModel : listActive.getModels().values()) {
                result.add(activeModel.getBlueprint().getName());
            }
        }
        return result;
    }

    public static void Set_Model(EntityTag entity, String model_id, double scale) {
        ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(entity.getBukkitEntity());
        ActiveModel activeModel = ModelEngineAPI.createActiveModel(model_id);
        activeModel.setScale(scale);
        activeModel.setHitboxScale(scale);
        modeledEntity.setSaved(true);
        modeledEntity.setBaseEntityVisible(false);
        modeledEntity.addModel(activeModel, true);
    }

    public static void Unset_Model(ModeledEntity modeledEntity) {
        modeledEntity.setSaved(false);
        modeledEntity.setBaseEntityVisible(true);
        for (ActiveModel activeModel : modeledEntity.getModels().values()) {
            modeledEntity.removeModel(activeModel.getBlueprint().getName()).ifPresent(ActiveModel::destroy);
        }
    }

    public static void Model_Tag(ModeledEntity modeledEntity, String bone_id, String string) {
        Collection<ActiveModel> activeModel = modeledEntity.getModels().values();
        for (ActiveModel a : activeModel) {
            a.getBone(bone_id).flatMap(modelBone -> modelBone.getBoneBehavior(BoneBehaviorTypes.NAMETAG)).ifPresent(tag -> {
                tag.setVisible(true);
                Debug.log(string);
                tag.setString(string);
            });
        }
    }

    public static void Model_Enchant(ModeledEntity modeledEntity, String bone_id, String set_enchant) {
        Collection<ActiveModel> activeModel = modeledEntity.getModels().values();
        for (ActiveModel a : activeModel) {
            for (ModelBone value : a.getBones().values()) {
                if (value.getUniqueBoneId().equalsIgnoreCase(bone_id) || bone_id.equalsIgnoreCase("*")) {
                    if (set_enchant.equalsIgnoreCase("toggle")) {
                        value.setEnchanted(!value.isEnchanted());
                    }else if (set_enchant.equalsIgnoreCase("true") || set_enchant.equalsIgnoreCase("false")) {
                        value.setEnchanted(Boolean.parseBoolean(set_enchant));
                    }
                }
            }
        }
    }
}