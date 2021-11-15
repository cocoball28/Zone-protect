package org.zone.command.zone.flags.interact.door;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.Permissions;
import org.zone.command.ZoneArguments;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.interact.door.DoorInteractionFlag;
import org.zone.region.group.Group;
import org.zone.region.group.SimpleGroup;

import java.util.Optional;

public class SetValueDoorInteractionFlagCommand {

    public static class Executor implements CommandExecutor {

        @Override
        public CommandResult execute(CommandContext context) {
            Zone zone = context.requireOne(ZONE);
            @NotNull DoorInteractionFlag flag =
                    zone.getFlag(FlagTypes.DOOR_INTERACTION).orElseGet(() -> new DoorInteractionFlag(DoorInteractionFlag.ELSE));
            Optional<Boolean> opValue = context.one(VALUE);
            if (opValue.isEmpty()) {
                zone.removeFlag(FlagTypes.DOOR_INTERACTION);
                try {
                    zone.save();
                    context.sendMessage(Identity.nil(), Component.text("Removed flag. Using default or parent flag value"));
                } catch (ConfigurateException e) {
                    e.printStackTrace();
                    return CommandResult.error(Component.text("Unable to save"));
                }
                return CommandResult.success();
            }
            flag.setValue(opValue.get());
            zone.addFlag(flag);
            try {
                zone.save();
                context.sendMessage(Identity.nil(), Component.text("Updated Door Interaction"));
            } catch (ConfigurateException e) {
                e.printStackTrace();
                return CommandResult.error(Component.text("Could not save: " + e.getMessage()));
            }
            return CommandResult.success();
        }
    }

    public static final Parameter.Value<Zone> ZONE = ZoneArguments
            .createZoneArgument((stream, context) -> stream.filter(zone -> {
                Subject subject = context.subject();
                if (!(subject instanceof Player p)) {
                    return zone.getParent().isEmpty();
                }
                if (context.hasPermission(Permissions.REGION_ADMIN_INFO.getPermission()) && zone.getParent().isEmpty()) {
                    return true;
                }

                Group group = zone.getMembers().getGroup(p.uniqueId());

                return group.equals(SimpleGroup.OWNER);
            }))
            .key("zone")
            .build();

    public static final Parameter.Value<Boolean> VALUE = Parameter.bool().key("value").optional().build();
}
