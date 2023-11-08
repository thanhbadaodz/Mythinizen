package aduvip.mythinizen.mythicmobs.targets;

import com.denizenscript.denizen.objects.EntityTag;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.SkillCaster;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.placeholders.PlaceholderDouble;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.skills.SkillExecutor;
import io.lumine.mythic.core.skills.targeters.IEntitySelector;
import java.util.Collection;
import java.util.HashSet;

public class Flagged extends IEntitySelector {
    private final PlaceholderString flag;
    private final PlaceholderString equals;
    private final PlaceholderString living;
    private final PlaceholderString onlyplayer;
    protected PlaceholderDouble radius;

    public Flagged(SkillExecutor manager, MythicLineConfig mlc) {
        super(manager, mlc);
        this.flag = PlaceholderString.of(mlc.getString(new String[] { "flag", "f" }, null));
        this.equals = PlaceholderString.of(mlc.getString(new String[] { "equals", "e" }, null));
        this.radius = PlaceholderDouble.of(mlc.getString(new String[] { "radius", "r" }, "5.0"));
        this.living = PlaceholderString.of(mlc.getString(new String[] { "living", "l" }, "true"));
        this.onlyplayer = PlaceholderString.of(mlc.getString(new String[] { "onlyplayer", "player", "p" }, "false"));
    }

    public Collection<AbstractEntity> getEntities(SkillMetadata data) {
        if (this.flag != null) {
            SkillCaster am = data.getCaster();
            HashSet<AbstractEntity> targets = new HashSet<>();
            for (AbstractEntity e : MythicBukkit.inst().getEntityManager().getLivingEntities(am.getEntity().getWorld())) {
                EntityTag entity = new EntityTag(e.getBukkitEntity());
                //EntityTag caster = new EntityTag(am.getEntity().getBukkitEntity());
                if (e.getWorld().equals(am.getEntity().getWorld()) &&
                    am.getEntity().getLocation().distanceSquared(e.getLocation()) < Math.pow(this.radius.get(data), 2.0D)){
                    if (this.equals == null) {
                        if (!entity.getFlagTracker().hasFlag(this.flag.get())) continue;
                    }else if (!String.valueOf(entity.getFlagTracker().getFlagValue(this.flag.get())).equalsIgnoreCase(this.equals.get())) {
                        continue;
                    }
                    switch (this.living.get() + entity.isLivingEntity()) {
                        case "true true", "false false" -> {
                            continue;
                        }
                    }
                    if(Boolean.parseBoolean(this.onlyplayer.get()) && !e.isPlayer()) continue;
                    targets.add(e);
                }
            }
            return targets;
        }
        return new HashSet<>();
    }
}
