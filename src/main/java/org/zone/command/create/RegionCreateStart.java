package org.zone.command.create;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.math.vector.Vector3i;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.ZoneBuilder;
import org.zone.region.regions.Region;
import org.zone.region.regions.type.PointRegion;

import java.util.Collections;
import java.util.Optional;

public final class RegionCreateStart {

    public static class Executor implements CommandExecutor {
        @Override
        public CommandResult execute(CommandContext context) {
            Subject subject = context.subject();
            if (!(subject instanceof ServerPlayer player)) {
                return CommandResult.error(Component.text("Player only command"));
            }

            String name = String.join(" ", context.all(NAME_PARAMETER));
            Vector3i vector3i = player.location().blockPosition();
            Region region = new PointRegion(player.world().key(), new Vector3i(vector3i.x(), 0, vector3i.z()),
                    new Vector3i(vector3i.x(), 256, vector3i.z()));

            ZoneBuilder builder = new ZoneBuilder()
                    .setName(name)
                    .setContainer(ZonePlugin.getZonesPlugin().getPluginContainer())
                    .setKey(name.toLowerCase().replaceAll(" ", "_"))
                    .setRegion(region);
            ZonePlugin.getZonesPlugin().getMemoryHolder().registerZoneBuilder(player.uniqueId(), builder);
            player.sendMessage(Component
                    .text("Region builder mode enabled. Run ")
                    .append(Component
                            .text("'/zone create end'")
                            .color(NamedTextColor.AQUA)));
            return CommandResult.success();
        }

    }

    public static final Parameter.Value<String> NAME_PARAMETER =
            Parameter.string().consumeAllRemaining().key("name").completer((context, currentInput) -> {
                String asId = currentInput.toLowerCase().replaceAll(" ", "_");
                Optional<Zone> opZone = ZonePlugin.getZonesPlugin().getZoneManager().getZone(asId);
                if (opZone.isPresent()) {
                    return Collections.singletonList(CommandCompletion.of("New " + currentInput, Component.text("That name " +
                            "has already been used").color(NamedTextColor.RED)));
                }

                return Collections.emptyList();
            }).build();

    private RegionCreateStart() {
        throw new RuntimeException("Should not init");
    }
}
