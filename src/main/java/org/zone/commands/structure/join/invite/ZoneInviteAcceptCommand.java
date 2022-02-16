package org.zone.commands.structure.join.invite;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.profile.GameProfile;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.sponge.UserArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.region.flag.meta.request.join.JoinRequestFlag;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneInviteAcceptCommand implements ArgumentCommand {

    public static final UserArgument USER = new UserArgument("user");

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("join"),
                             new ExactArgument("accept"),
                             USER);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Accept the join request of a player");
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        GameProfile players = commandContext.getArgument(this, USER);
        JoinRequestFlag joinRequestFlag =
        return CommandResult.success();
    }
}
