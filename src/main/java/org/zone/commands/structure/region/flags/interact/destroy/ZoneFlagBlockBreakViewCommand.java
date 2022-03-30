package org.zone.commands.structure.region.flags.interact.destroy;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.OptionalArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilterBuilder;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilters;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.interact.block.destroy.BlockBreakFlag;
import org.zone.region.group.key.GroupKeys;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Used for viewing the status of the {@link BlockBreakFlag}
 */
public class ZoneFlagBlockBreakViewCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE = new ZoneArgument("zoneId",
            ZonePermissions.OVERRIDE_FLAG_BLOCK_INTERACTION_BREAK_VIEW,
            new ZoneArgumentFilterBuilder()
                    .setFilter(ZoneArgumentFilters.withGroupKey(GroupKeys.OWNER))
                    .build());

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                new ExactArgument("flag"),
                ZONE,
                new ExactArgument("interact"),
                new ExactArgument("block"),
                new ExactArgument("break"),
                OptionalArgument.createArgument(new ExactArgument("view")));
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getBlockBreakViewCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_BLOCK_INTERACTION_BREAK_VIEW);
    }

    @Override
    public @NotNull CommandResult run(@NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE);
        Optional<BlockBreakFlag> opFlag = zone.getFlag(FlagTypes.BLOCK_BREAK);
        commandContext.sendMessage(Messages.getEnabledInfo(opFlag.isPresent()));
        opFlag
                .flatMap(flag -> zone.getMembers().getGroup(flag.getRequiredKey()))
                .ifPresent(Messages::getGroupInfo);
        return CommandResult.success();
    }
}
