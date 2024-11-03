package aduvip.mythinizen.mythicmobs.conditions;

import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizencore.flags.AbstractFlagTracker;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.objects.ObjectType;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.objects.core.ListTag;
import com.denizenscript.denizencore.objects.core.MapTag;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.conditions.IEntityCondition;
import io.lumine.mythic.core.skills.SkillCondition;

public class flagContains extends SkillCondition implements IEntityCondition {
    private final String flag;
    private final String containsValue;

    public flagContains(MythicLineConfig mlc) {
        super(mlc.getLine());
        this.flag = mlc.getString(new String[] { "flag", "f" }, null, new String[] { this.conditionVar });
        this.containsValue = mlc.getString(new String[] { "contains", "c" }, null, new String[] { this.conditionVar });
    }

    public boolean check(AbstractEntity entity) {
        EntityTag denizenntity =  new EntityTag(entity.getBukkitEntity());
        AbstractFlagTracker object = denizenntity.getFlagTracker();
        if (object.hasFlag(this.flag)){
            if (object.getFlagValue(flag).canBeType(ListTag.class) ){
                return ((ListTag) object.getFlagValue(flag)).contains(new ElementTag(this.containsValue));
            }else if(object.getFlagValue(flag).canBeType(MapTag.class)){
                return ((MapTag) object.getFlagValue(flag)).containsKey(this.containsValue);
            }
        }
        return false;
    }
}