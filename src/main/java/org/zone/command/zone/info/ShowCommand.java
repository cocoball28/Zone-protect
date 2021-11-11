package org.zone.command.zone.info;

import net.kyori.adventure.text.Component;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.effect.Viewer;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.world.Locatable;
import org.spongepowered.math.vector.Vector3i;
import org.zone.ZonePlugin;
import org.zone.command.ZoneArguments;
import org.zone.event.listener.PlayerListener;
import org.zone.region.Zone;
import org.zone.region.regions.BoundedRegion;

import java.util.concurrent.TimeUnit;

public class ShowCommand {

    public static final class Executor implements CommandExecutor {

        @Override
        public CommandResult execute(CommandContext context) {
            Subject subject = context.subject();
            if (!(subject instanceof Viewer viewer && subject instanceof Locatable locatable)) {
                return CommandResult.error(Component.text("Player only command"));
            }

            Zone zone = context.requireOne(ZONE);
            if (!(zone.getRegion() instanceof BoundedRegion region)) {
                return CommandResult.error(Component.text("That zones region style is not yet supported"));
            }

            PlayerListener.runOnOutside(region, locatable.blockPosition().y() - 1,
                    vector3i -> viewer.sendBlockChange(vector3i, BlockTypes.ORANGE_WOOL.get().defaultState()),
                    zone.getParentId().isPresent());
            Sponge
                    .server()
                    .scheduler()
                    .submit(Task
                            .builder()
                            .plugin(ZonePlugin.getZonesPlugin().getPluginContainer())
                            .delay(10, TimeUnit.SECONDS)
                            .execute(() -> PlayerListener
                                    .runOnOutside(region, locatable.blockPosition().y() - 1,
                                            viewer::resetBlockChange,
                                            zone
                                                    .getParentId()
                                                    .isPresent()))
                            .build());
            return CommandResult.success();
        }
    }

    public static final Parameter.Value<Zone> ZONE = ZoneArguments
            .createZoneArgument((stream, context) -> stream
                    .filter(zone -> zone.getRegion() instanceof BoundedRegion)
                    .filter(zone -> {
                        if (!(context.subject() instanceof Locatable locatable)) {
                            return true;
                        }
                        Vector3i vector3i = zone.getRegion().getNearestPosition(locatable.blockPosition());
                        return (vector3i.distanceSquared(locatable.blockPosition()) <= 40);
                    }))
            .key("zone")
            .build();
}
