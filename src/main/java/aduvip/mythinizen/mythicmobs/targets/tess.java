package aduvip.mythinizen.mythicmobs.targets;

import com.google.common.collect.Lists;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.adapters.AbstractLocation;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.placeholders.PlaceholderDouble;
import io.lumine.mythic.core.skills.SkillExecutor;
import io.lumine.mythic.core.skills.targeters.IEntitySelector;

import java.util.Collection;
import java.util.function.Predicate;

public class tess extends IEntitySelector {
    protected PlaceholderDouble radius;

    protected Predicate<AbstractEntity> livingOnly;

    public tess(SkillExecutor manager, MythicLineConfig mlc) {
        super(manager, mlc);
        this.radius = PlaceholderDouble.of(mlc.getString(new String[] { "radius", "r" }, "5"));
        boolean livingOnly = mlc.getBoolean(new String[] { "livingonly", "living", "l" }, true);
        if (livingOnly) {
            this.livingOnly = AbstractEntity::isLiving;
        } else {
            this.livingOnly = (ae -> true);
        }
    }

    public Collection<AbstractEntity> getEntities(SkillMetadata data) {
        return getEntitiesNearPoint(data, this.livingOnly, data.getCaster().getLocation());
    }

    protected Collection<AbstractEntity> getEntitiesNearPoint(SkillMetadata data, Predicate<AbstractEntity> filter, AbstractLocation location) {
        Collection<AbstractEntity> targets = Lists.newArrayList();
        double radiusSq = Math.pow(this.radius.get(data), 2.0D);
        Predicate<AbstractEntity> entityFilter = ae -> this.livingOnly.test(ae) && (filter == null || filter.test(ae));
        for (AbstractEntity p : location.getWorld().getEntitiesNearLocation(location, this.radius.get(data), entityFilter)) {
            if (!p.getUniqueId().equals(data.getCaster().getEntity().getUniqueId()) &&
                    location.distanceSquared(p.getLocation()) < radiusSq)
                targets.add(p);
        }
        return targets;
    }
}
