package aduvip.mythinizen.mythicmobs.mechanics;

import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizen.utilities.Settings;
import com.denizenscript.denizen.utilities.implementation.BukkitScriptEntryData;
import com.denizenscript.denizencore.scripts.ScriptBuilder;
import com.denizenscript.denizencore.scripts.ScriptEntry;
import com.denizenscript.denizencore.scripts.ScriptEntryData;
import com.denizenscript.denizencore.scripts.queues.core.InstantQueue;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.bukkit.BukkitAdapter;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class denizen implements ITargetedEntitySkill {

    final PlaceholderString command;
    final PlaceholderString args;

    public denizen(MythicLineConfig mlc) {
        this.command = mlc.getPlaceholderString(new String[] {"command","c"},"narrate");
        this.args = mlc.getPlaceholderString(new String[] {"args","a"},"hi");
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        Entity caster = BukkitAdapter.adapt(data.getCaster().getEntity());
        List<Object> entries = new ArrayList();
        String parsed_args = this.args.get(data, target);
        String entry = "%s %s".formatted(this.command.get(),parsed_args);
        entries.add(entry);
        InstantQueue queue = new InstantQueue("Mythinizen");
        List<ScriptEntry> scriptEntries = ScriptBuilder.buildScriptEntries(entries, null, (ScriptEntryData)new BukkitScriptEntryData(new EntityTag(caster)));
        queue.addEntries(scriptEntries);
        queue.start();
        return SkillResult.SUCCESS;
    }
}

