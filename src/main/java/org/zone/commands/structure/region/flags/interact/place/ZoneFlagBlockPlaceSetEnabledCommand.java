package org.zone.commands.structure.region.flags.interact.place;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.structure.misc.Messages;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.simple.BooleanArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.interact.block.place.BlockPlaceFlag;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Used to set the enabled status in {@link BlockPlaceFlag}
 */
public class ZoneFlagBlockPlaceSetEnabledCommand implements ArgumentCommand {

    public static final BooleanArgument VALUE = new BooleanArgument("enabledValue");
    public static final ZoneArgument ZONE = new ZoneArgument("zoneId");

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("flag"),
                             ZONE,
                             new ExactArgument("interact"),
                             new ExactArgument("block"),
                             new ExactArgument("place"),
                             new ExactArgument("set"),
                             new ExactArgument("enabled"),
                             VALUE);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Sets if the prevention to break blocks is enabled");
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
                    .sendMessage(Identity.nil(), Messages.getUniversalOnlyZoneFlagBlockPlaceCommandFlagSaved());
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return CommandResult.error(Messages.getUniversalZoneSavingError(e));
        }
        return CommandResult.success();
    }
}
