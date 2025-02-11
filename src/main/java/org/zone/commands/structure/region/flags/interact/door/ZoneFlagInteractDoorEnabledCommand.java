package org.zone.commands.structure.region.flags.interact.door;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.simple.BooleanArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.interact.door.DoorInteractionFlag;
import org.zone.region.group.key.GroupKeys;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Used for changing the status of the {@link DoorInteractionFlag}
 */
public class ZoneFlagInteractDoorEnabledCommand implements ArgumentCommand {
    public static final ZoneArgument ZONE = new ZoneArgument("zoneId",
                                                             new ZoneArgument.ZoneArgumentPropertiesBuilder().setLevel(
                                                                     GroupKeys.INTERACT_DOOR));

    public static final BooleanArgument VALUE = new BooleanArgument("enabledValue",
                                                                    "enable",
                                                                    "disable");

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("flag"),
                             ZONE,
                             new ExactArgument("interact"),
                             new ExactArgument("door"),
                             new ExactArgument("set"),
                             VALUE);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Sets if interaction with door should be enabled");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(CommandContext commandContext, String... args) {
        Zone zone = commandContext.getArgument(this, ZONE);
        boolean value = commandContext.getArgument(this, VALUE);
        if (value) {
            zone.addFlag(FlagTypes.BLOCK_BREAK.createCopyOfDefault());
        } else {
            zone.removeFlag(FlagTypes.BLOCK_BREAK);
        }
        try {
            zone.save();
            commandContext
                    .getCause()
                    .sendMessage(Identity.nil(),
                                 Messages.getUpdatedMessage(FlagTypes.DOOR_INTERACTION));
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(e));
        }
        return CommandResult.success();
    }
}
