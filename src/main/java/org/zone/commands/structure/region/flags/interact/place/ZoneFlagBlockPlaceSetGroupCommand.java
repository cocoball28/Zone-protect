package org.zone.commands.structure.region.flags.interact.place;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.ZoneGroupArgument;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilterBuilder;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilters;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.interact.block.place.BlockPlaceFlag;
import org.zone.region.group.Group;
import org.zone.region.group.key.GroupKeys;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Used to set the group status in {@link BlockPlaceFlag}
 */
public class ZoneFlagBlockPlaceSetGroupCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE = new ZoneArgument("zoneId",
            ZonePermissions.OVERRIDE_FLAG_BLOCK_INTERACTION_PLACE_SET_GROUP,
            new ZoneArgumentFilterBuilder()
                    .setFilter(ZoneArgumentFilters.withGroupKey(GroupKeys.OWNER))
                    .build());
    public static final ZoneGroupArgument GROUP = new ZoneGroupArgument("groupId", ZONE);

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                new ExactArgument("flag"),
                ZONE,
                new ExactArgument("interact"),
                new ExactArgument("block"),
                new ExactArgument("place"),
                new ExactArgument("set"),
                new ExactArgument("group"),
                GROUP);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getBlockPlaceSetGroupCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_BLOCK_INTERACTION_PLACE_SET_GROUP);
    }

    @Override
    public @NotNull CommandResult run(@NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE);
        @NotNull BlockPlaceFlag flag = zone
                .getFlag(FlagTypes.BLOCK_PLACE)
                .orElseGet(FlagTypes.BLOCK_PLACE::createCopyOfDefault);
        Group newGroup = commandContext.getArgument(this, GROUP);
        zone.getMembers().addKey(newGroup, flag.getRequiredKey());
        zone.setFlag(flag);
        try {
            zone.save();
            commandContext
                    .getCause()
                    .sendMessage(Identity.nil(), Messages.getUpdatedMessage(FlagTypes.BLOCK_PLACE));
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(e));
        }
        return CommandResult.success();
    }
}
