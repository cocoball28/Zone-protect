package org.zone.commands.structure.create.chunk;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.CommandResult;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.context.CommandContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneCreateChunkRegionCommand implements ArgumentCommand {

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList();
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Create a zone by specifying the position of the chunk");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(@NotNull CommandContext commandContext, @NotNull String[] args) {

        return CommandResult.success();
    }

    @Override
    public boolean hasPermission(@NotNull CommandCause source) {
        return ArgumentCommand.super.hasPermission(source);
    }
}
