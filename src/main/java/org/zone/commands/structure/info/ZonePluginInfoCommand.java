package org.zone.commands.structure.info;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.simple.StringArgument;
import org.zone.commands.system.context.CommandContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZonePluginInfoCommand implements ArgumentCommand {

    public static final ExactArgument ZONE = new ExactArgument("zone");
    public static final ExactArgument PLUGIN = new ExactArgument("plugin");
    public static final StringArgument INFO = new StringArgument("info");

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(ZONE, PLUGIN, INFO);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Command to show the plugin info");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(@NotNull CommandContext commandContext, @NotNull String... args) {
        return CommandResult.success();
    }

}
