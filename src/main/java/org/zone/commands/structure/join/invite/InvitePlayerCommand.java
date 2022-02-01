package org.zone.commands.structure.join.invite;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.living.player.Player;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.region.Zone;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class InvitePlayerCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId", new ZoneArgument.ZoneArgumentPropertiesBuilder().setVisitorOnly(false));

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("invite"), ZONE_ID);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Invite a player to a zone");
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        if (!(commandContext.getSource() instanceof Player player)) {
            return CommandResult.error(Messages.getPlayerOnlyMessage());
        }
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        return CommandResult.success();
    }
}
