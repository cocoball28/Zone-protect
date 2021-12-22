package org.zone.commands.structure.zone.flags.interact.door;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.NotEnoughArgumentsException;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.OptionalArgument;
import org.zone.commands.system.arguments.simple.BooleanArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.interact.door.DoorInteractionFlag;
import org.zone.region.group.key.GroupKeys;

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

    public static final OptionalArgument<Boolean> VALUE = new OptionalArgument<>(new BooleanArgument(
            "enabledValue"), (Boolean) null);

    @Override
    public List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("zone"),
                             new ExactArgument("flag"),
                             ZONE,
                             new ExactArgument("interact"),
                             new ExactArgument("door"),
                             new ExactArgument("set"),
                             new ExactArgument("enabled"),
                             VALUE);
    }

    @Override
    public Component getDescription() {
        return Component.text("sets if interaction with door should be enabled");
    }

    @Override
    public Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public CommandResult run(CommandContext commandContext, String... args) throws
            NotEnoughArgumentsException {
        Zone zone = commandContext.getArgument(this, ZONE);
        @NotNull DoorInteractionFlag flag = zone
                .getFlag(FlagTypes.DOOR_INTERACTION)
                .orElseGet(() -> new DoorInteractionFlag(DoorInteractionFlag.ELSE));
        @Nullable Boolean value = commandContext.getArgument(this, VALUE);
        if (value == null) {
            zone.removeFlag(FlagTypes.DOOR_INTERACTION);
            try {
                zone.save();
                commandContext
                        .getCause()
                        .sendMessage(Identity.nil(),
                                     Component.text("Removed flag. Using default " +
                                                            "or parent " +
                                                            "flag value"));
            } catch (ConfigurateException e) {
                e.printStackTrace();
                return CommandResult.error(Component.text("Unable to save"));
            }
            return CommandResult.success();
        }
        flag.setEnabled(value);
        zone.setFlag(flag);
        try {
            zone.save();
            commandContext
                    .getCause()
                    .sendMessage(Identity.nil(), Component.text("Updated Block Break"));
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return CommandResult.error(Component.text("Could not save: " + e.getMessage()));
        }
        return CommandResult.success();
    }
}
