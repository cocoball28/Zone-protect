package org.zone.commands.structure.region.flags.display.greetings;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;

import java.util.List;
import java.util.Optional;

public class ZoneFlagGreetingsDisplaySetTitleStayCommand implements ArgumentCommand {

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return null;
    }

    @Override
    public @NotNull Component getDescription() {
        return null;
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        return null;
    }

}
