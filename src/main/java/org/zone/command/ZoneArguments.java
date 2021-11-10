package org.zone.command;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.entity.living.player.Player;
import org.zone.ZonePlugin;
import org.zone.region.Zone;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ZoneArguments {

    private ZoneArguments() {
        throw new RuntimeException("Should not init");
    }

    public static Parameter.Value.Builder<Zone> createZoneArgument(@NotNull BiFunction<? super Stream<Zone>, ? super CommandContext, ? extends Stream<Zone>> streamFunc) {
        return Parameter.builder(Zone.class).addParser((parameterKey, reader,
                                                        context) -> {
                    String zoneName = reader.parseUnquotedString();
                    Stream<Zone> stream = ZonePlugin.getZonesPlugin()
                            .getZoneManager()
                            .getZones()
                            .parallelStream()
                            .filter(zone -> zone.getId().equalsIgnoreCase(zoneName));
                    return streamFunc.apply(stream, context)
                            .findAny();
                })
                .completer((context, currentInput) -> {
                    Stream<Zone> zonesStream = ZonePlugin.getZonesPlugin()
                            .getZoneManager()
                            .getZones()
                            .parallelStream()
                            .filter(zone -> zone.getParent().isEmpty())
                            .filter(zone -> zone.getId().toLowerCase().contains(currentInput.toLowerCase()) || zone.getName().toLowerCase().contains(currentInput.toLowerCase()));
                    List<Zone> zones = streamFunc
                            .apply(zonesStream, context)
                            .collect(Collectors.toList());
                    if (context.subject() instanceof Player player) {
                        zones.sort((z1, z2) -> {
                            boolean zc1 = z1.getMembers().getMembers().contains(player.uniqueId());
                            boolean zc2 = z2.getMembers().getMembers().contains(player.uniqueId());
                            if (zc1 && zc2 || !zc1 && !zc2) {
                                return 0;
                            }
                            if (zc1) {
                                return 1;
                            }
                            return -1;
                        });
                    }
                    return zones.stream().map(zone -> CommandCompletion.of(zone.getId(),
                            Component.text(zone.getName()))).collect(Collectors.toList());
                });
    }
}
