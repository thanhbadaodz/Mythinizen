package aduvip.mythinizen.mythicmobs.conditions;

import com.denizenscript.denizen.objects.EntityTag;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.conditions.IEntityCondition;
import io.lumine.mythic.core.skills.SkillCondition;

public class flagged extends SkillCondition implements IEntityCondition {
    private final String flag;

    public flagged(MythicLineConfig mlc) {
        super(mlc.getLine());
        this.flag = mlc.getString(new String[] { "flag", "f" }, null, new String[] { this.conditionVar });
    }

    public boolean check(AbstractEntity entity) {
        EntityTag denizenntity =  new EntityTag(entity.getBukkitEntity());
        return denizenntity.getFlagTracker().hasFlag(flag);
    }
}