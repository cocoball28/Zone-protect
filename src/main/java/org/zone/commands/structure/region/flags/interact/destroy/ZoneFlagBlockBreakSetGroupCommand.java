package org.zone.commands.structure.region.flags.interact.destroy;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.structure.misc.Messages;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.ZoneGroupArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.interact.block.destroy.BlockBreakFlag;
import org.zone.region.group.Group;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Used for changing the group status of {@link BlockBreakFlag}
 */
public class ZoneFlagBlockBreakSetGroupCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE = new ZoneArgument("zoneId");
    public static final ZoneGroupArgument GROUP = new ZoneGroupArgument("groupId", ZONE);

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("flag"),
                             ZONE,
                             new ExactArgument("interact"),
                             new ExactArgument("block"),
                             new ExactArgument("break"),
                             new ExactArgument("group"),
                             GROUP);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Sets the group ");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(CommandContext commandContext, String... args) {
        Zone zone = commandContext.getArgument(this, ZONE);
        BlockBreakFlag flag = zone
                .getFlag(FlagTypes.BLOCK_BREAK)
                .orElseGet(FlagTypes.BLOCK_BREAK::createCopyOfDefault);
        Group newGroup = commandContext.getArgument(this, GROUP);
        zone.getMembers().addKey(newGroup, flag.getRequiredKey());
        zone.setFlag(flag);
        try {
            zone.save();
            commandContext
                    .getCause()
                    .sendMessage(Identity.nil(), Messages.getZoneFlagBlockBreakSetGroupCommandGroupUpdatedFlagSaved());
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return CommandResult.error(Messages.setUniversalZoneSavingErrorMessage(e));
        }
        return CommandResult.success();
    }
}
