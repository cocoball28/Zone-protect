package org.zone.commands.structure.leave;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.living.player.Player;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.misc.Messages;
import org.zone.region.Zone;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class LeaveZoneCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE = new ZoneArgument("zone",
                                                             new ZoneArgument.ZoneArgumentPropertiesBuilder().setLimitedToOnlyPartOf(
                                                                     true));

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("leave"), ZONE);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Leaves a zone");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(@NotNull CommandContext commandContext,
                                      @NotNull String... args) {
        if (!(commandContext.getSource() instanceof Player player)) {
            return CommandResult.error(Messages.getPlayerOnlyMessage());
        }
        Zone zone = commandContext.getArgument(this, ZONE);
        zone.getMembers().removeMember(player.uniqueId());
        player.sendMessage(Messages.getLeftZoneMembersMessage(zone));
        return CommandResult.success();
    }
}
