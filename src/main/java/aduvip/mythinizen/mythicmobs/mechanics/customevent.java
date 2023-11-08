package aduvip.mythinizen.mythicmobs.mechanics;

import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizencore.events.core.CustomScriptEvent;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.objects.core.MapTag;
import com.denizenscript.denizencore.scripts.ScriptEntry;
import com.denizenscript.denizencore.scripts.containers.ScriptContainer;
import com.denizenscript.denizencore.utilities.YamlConfiguration;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.bukkit.BukkitAdapter;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import org.bukkit.entity.Entity;


public class customevent implements ITargetedEntitySkill {

    final PlaceholderString id;
    final PlaceholderString context;

    public customevent(MythicLineConfig mlc) {

        this.id = mlc.getPlaceholderString(new String[] {"id","i"},null);
        this.context = mlc.getPlaceholderString(new String[] {"context","c"},null);
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        if (this.id.get() != null) {
            String id = this.id.get(data, target);
            String[] raw_context = this.context.get(data, target).split(" ");
            YamlConfiguration yamlConfiguration = new YamlConfiguration();
            ScriptContainer script = new ScriptContainer(yamlConfiguration, id);
            Entity caster = BukkitAdapter.adapt(data.getCaster().getEntity());
            try {
                ScriptEntry entry = new ScriptEntry("", new String[0], script);
                entry.setScript(id);
                //
                MapTag context = new MapTag();
                context.putObject("caster", new EntityTag(caster));
                context.putObject("target", new EntityTag(target.getBukkitEntity()));
                for (String raw : raw_context) {
                    String[] pair = raw.split(":");
                    if (pair.length == 2) context.putObject(pair[0], new ElementTag(pair[1]));
                }
                //
                CustomScriptEvent ranEvent = CustomScriptEvent.runCustomEvent(entry.entryData, id, context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return SkillResult.SUCCESS;
    }
}

