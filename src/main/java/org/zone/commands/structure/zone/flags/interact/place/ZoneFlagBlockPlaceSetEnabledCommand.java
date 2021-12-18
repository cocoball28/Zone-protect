package org.zone.commands.structure.zone.flags.interact.place;

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
import org.zone.commands.system.arguments.simple.BooleanArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.interact.block.place.BlockPlaceFlag;
import org.zone.region.group.key.GroupKeys;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Used to set the enabled status in {@link BlockPlaceFlag}
 */
public class ZoneFlagBlockPlaceSetEnabledCommand implements ArgumentCommand {

    public static final BooleanArgument VALUE = new BooleanArgument("enabledValue");
    public static final ZoneArgument ZONE = new ZoneArgument("zoneId", new ZoneArgument.ZoneArgumentPropertiesBuilder().setLevel(GroupKeys.OWNER));

    @Override
    public List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("zone"), new ExactArgument("flag"), ZONE, new ExactArgument("interact"), new ExactArgument("block"), new ExactArgument("place"), new ExactArgument("set"), new ExactArgument("enabled"), VALUE);
    }

    @Override
    public Component getDescription() {
        return Component.text("Sets if the prevention to break blocks is enabled");
    }

    @Override
    public Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public CommandResult run(CommandContext commandContext, String... args) throws NotEnoughArgumentsException {
        Zone zone = commandContext.getArgument(this, ZONE);
        @NotNull BlockPlaceFlag flag = zone
                .getFlag(FlagTypes.BLOCK_PLACE)
                .orElseGet(() -> new BlockPlaceFlag(BlockPlaceFlag.DEFAULT));
        @Nullable Boolean value = commandContext.getArgument(this, VALUE);
        if (value == null) {
            zone.removeFlag(FlagTypes.BLOCK_PLACE);
            try {
                zone.save();
                commandContext
                        .getCause()
                        .sendMessage(Identity.nil(), Component.text("Removed flag. Using default " +
                                "or parent " +
                                "flag value"));
            } catch (ConfigurateException e) {
                e.printStackTrace();
                return CommandResult.error(Component.text("Unable to save"));
            }
            return CommandResult.success();
        }
        flag.setEnabled(value);
        zone.addFlag(flag);
        try {
            zone.save();
            commandContext.getCause().sendMessage(Identity.nil(), Component.text("Updated Block placement"));
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return CommandResult.error(Component.text("Could not save: " + e.getMessage()));
        }
        return CommandResult.success();
    }
}
