package org.zone.commands.structure.create.chunk;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.math.vector.Vector3i;
import org.zone.ZonePlugin;
import org.zone.utils.Messages;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.RemainingArgument;
import org.zone.commands.system.arguments.simple.StringArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.ZoneBuilder;
import org.zone.region.bounds.BoundedRegion;
import org.zone.region.bounds.ChildRegion;
import org.zone.region.bounds.mode.BoundModes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ZoneCreateChunkStartCommand implements ArgumentCommand {

    public static final ExactArgument CREATE = new ExactArgument("create");
    public static final ExactArgument BOUNDS = new ExactArgument("bounds");
    public static final ExactArgument CHUNK = new ExactArgument("chunk");
    public static final ExactArgument START = new ExactArgument("start");
    public static final RemainingArgument<String> NAME = new RemainingArgument<>(new StringArgument("name"));

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(CREATE, BOUNDS, CHUNK, START, NAME);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Creates a chunk zone");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(@NotNull CommandContext commandContext, @NotNull String... args) {
        Subject subject = commandContext.getSource();
        if (!(subject instanceof ServerPlayer player)) {
            return CommandResult.error(Messages.getPlayerOnlyMessage());
        }

        String name = String.join(" ", commandContext.getArgument(this, NAME));
        Vector3i vector3i = player.location().blockPosition();
        BoundedRegion region = new BoundedRegion(new Vector3i(vector3i.x(), 0, vector3i.z()),
                                                 new Vector3i(vector3i.x(), 256, vector3i.z()));

        ChildRegion childRegion = new ChildRegion(Collections.singleton(region));

        ZoneBuilder builder = new ZoneBuilder()
                .setName(name)
                .setContainer(ZonePlugin.getZonesPlugin().getPluginContainer())
                .setKey(name.toLowerCase().replaceAll(" ", "_"))
                .setWorld(player.world())
                .setRegion(childRegion)
                .setBoundMode(BoundModes.CHUNK);
        if (ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getZone(builder.getContainer(), builder.getKey())
                .isPresent()) {
            return CommandResult.error(Messages.getDuplicateNameError());
        }
        ZonePlugin
                .getZonesPlugin()
                .getMemoryHolder()
                .registerZoneBuilder(player.uniqueId(), builder);
        player.sendMessage(Messages.getZoneRegionBuilderEnabled());
        return CommandResult.success();
    }

    @Override
    public boolean hasPermission(@NotNull CommandCause source) {
        if (!(source.subject() instanceof Player)) {
            return false;
        }
        return ArgumentCommand.super.hasPermission(source);
    }
}