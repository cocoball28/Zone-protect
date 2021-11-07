package org.zone.command.create;

import net.kyori.adventure.text.Component;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.math.vector.Vector3i;
import org.zone.Permissions;
import org.zone.ZonePlugin;
import org.zone.player.PlayerKeys;
import org.zone.region.ZoneBuilder;
import org.zone.region.regions.Region;
import org.zone.region.regions.type.PointRegion;

public class RegionCreateStart {

    public static class Executor implements CommandExecutor {
        @Override
        public CommandResult execute(CommandContext context) {
            Subject subject = context.subject();
            if (!(subject instanceof Player)) {
                return CommandResult.error(Component.text("Player only command"));
            }

            Player player = (Player) subject;
            String name = context.requireOne(NAME_PARAMETER);
            Vector3i vector3i = player.location().blockPosition();
            Region region = new PointRegion(player.world(), new Vector3i(vector3i.x(), 0, vector3i.z()),
                    new Vector3i(vector3i.x(), 256, vector3i.z()));

            ZoneBuilder builder = new ZoneBuilder()
                    .setName(name.replaceAll("_", " "))
                    .setContainer(ZonePlugin.getInstance().getContainer())
                    .setKey(name)
                    .setRegion(region);
            player.offer(PlayerKeys.REGION_BUILDER, builder);
            return CommandResult.success();
        }

    }
    public static final Parameter.Value<String> NAME_PARAMETER = Parameter.string().build();
}
