package org.zone.commands.structure.create.chunk;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.service.permission.Subject;
import org.zone.ZonePlugin;
import org.zone.misc.Messages;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.RemainingArgument;
import org.zone.commands.system.arguments.simple.StringArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.ZoneBuilder;
import org.zone.region.bounds.BoundedRegion;
import org.zone.region.bounds.ChildRegion;
import org.zone.region.bounds.mode.BoundModes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ZoneCreateChunkSubStartCommand implements ArgumentCommand {

    public static final ExactArgument CREATE = new ExactArgument("create");
    public static final ExactArgument SUB = new ExactArgument("sub");
    public static final ExactArgument CHUNK = new ExactArgument("chunk");
    public static final ZoneArgument ZONE = new ZoneArgument("zone", new ZoneArgument.ZoneArgumentPropertiesBuilder());
    public static final RemainingArgument<String> NAME = new RemainingArgument<>(new StringArgument("name"));

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(CREATE, SUB, CHUNK, ZONE, NAME);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Creates a sub chunk zone");
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
        Zone zone = commandContext.getArgument(this, ZONE);
        String name = String.join(" ", commandContext.getArgument(this, NAME));

        BoundedRegion region = new BoundedRegion(player.blockPosition().add(0, -1, 0),
                                                 player.blockPosition());

        ChildRegion childRegion = new ChildRegion(Collections.singleton(region));

        ZoneBuilder builder = new ZoneBuilder()
                .setParent(zone)
                .setKey(zone.getKey() + "_" + name.toLowerCase().replaceAll(" ", "_"))
                .setName(name)
                .setRegion(childRegion)
                .setContainer(ZonePlugin.getZonesPlugin().getPluginContainer())
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

}