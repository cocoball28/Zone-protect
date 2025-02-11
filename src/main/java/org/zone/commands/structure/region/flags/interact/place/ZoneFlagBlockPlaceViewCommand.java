package org.zone.commands.structure.region.flags.interact.place;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.zone.Identifiable;
import org.zone.Permissions;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.OptionalArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.interact.block.place.BlockPlaceFlag;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Used to view the status of {@link BlockPlaceFlag}
 */
public class ZoneFlagBlockPlaceViewCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE = new ZoneArgument("zoneId");

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("flag"),
                             ZONE,
                             new ExactArgument("interact"),
                             new ExactArgument("block"),
                             new ExactArgument("place"),
                             new OptionalArgument<>(new ExactArgument("view"), (String) null));
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("View the details on block placement");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.of(Permissions.REGION_ADMIN_INFO.getPermission());
    }

    @Override
    public @NotNull CommandResult run(CommandContext commandContext, String... args) {
        Zone zone = commandContext.getArgument(this, ZONE);
        Optional<BlockPlaceFlag> opFlag = zone.getFlag(FlagTypes.BLOCK_PLACE);
        commandContext
                .getCause()
                .sendMessage(Identity.nil(), Component.text("Enabled: " + opFlag.isPresent()));
        commandContext
                .getCause()
                .sendMessage(Identity.nil(),
                             Component.text("Group: " +
                                                    opFlag
                                                            .map(flag -> zone
                                                                    .getMembers()
                                                                    .getGroup(flag.getRequiredKey())
                                                                    .map(Identifiable::getName)
                                                                    .orElse("None"))
                                                            .orElse("None")));
        return CommandResult.success();
    }
}
