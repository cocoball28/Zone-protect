package org.zone.region.flag.entity.player.move.preventing;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOptions;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.util.Ticks;
import org.spongepowered.api.world.Locatable;
import org.spongepowered.api.world.Location;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3i;
import org.zone.ZonePlugin;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKeys;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PreventPlayersListener {

    @Listener(order = Order.FIRST)
    public void onPlayerEnter(MoveEntityEvent event, @Getter("entity") Player player) {
        if (event.originalPosition().toInt().equals(event.destinationPosition().toInt())) {
            return;
        }

        if (player instanceof Subject subject) {
            if (ZonePermissions.BYPASS_ENTRY.hasPermission(subject)) {
                return;
            }
        }

        Optional<Zone> opPreviousZone = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getPriorityZone(event.entity().world(), event.originalPosition());

        if (opPreviousZone.isPresent()) {
            return;
        }

        Optional<Zone> opNextZone = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getPriorityZone(event.entity().world(), event.destinationPosition());

        if (opNextZone.isEmpty()) {
            //player is not moving into a zone, ignore this flag
            return;
        }

        Zone zone = opNextZone.get();
        Optional<PreventPlayersFlag> opFlag = zone.getFlag(FlagTypes.PREVENT_PLAYERS);
        if (opFlag.isEmpty()) {
            return;
        }

        if (opFlag.get().hasPermission(zone, player.uniqueId())) {
            return;
        }
        event.setCancelled(true);
    }

    @Listener(order = Order.EARLY)
    public void onPlayerLoginEvent(ServerSideConnectionEvent.Join joinEvent) {
        ServerPlayer serverPlayer = joinEvent.player();
        Optional<Zone> opZone = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getPriorityZone(joinEvent.player().world(), joinEvent.player().position());

        if (opZone.isEmpty()) {
            return;
        }

        Optional<PreventPlayersFlag> flag = opZone.get().getFlag(FlagTypes.PREVENT_PLAYERS);
        if (flag.isEmpty()) {
            return;
        }
        if (!flag.get().hasPermission(opZone.get(), serverPlayer.uniqueId())) {
            return;
        }
        Optional<Vector3i> opPos = getOutsidePosition(opZone.get(), serverPlayer);
        if (opPos.isPresent()) {
            serverPlayer.setPosition(opPos.get().toDouble());
            return;
        }
        serverPlayer.setPosition(serverPlayer.world().properties().spawnPosition().toDouble());
    }

    @Listener
    public void onEntityStuck(MoveEntityEvent event, @Getter("entity") Player player) {
        if (player instanceof Subject subject) {
            if (ZonePermissions.BYPASS_ENTRY.hasPermission(subject)) {
                return;
            }
        }
        Optional<Zone> opPreviousZone = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getPriorityZone(event.entity().world(), event.originalPosition());
        if (opPreviousZone.isEmpty()) {
            return;
        }

        Optional<Zone> opNextZone = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getPriorityZone(event.entity().world(), event.destinationPosition());
        if (opNextZone.isEmpty()) {
            return;
        }

        Zone zone = opNextZone.get();
        Optional<PreventPlayersFlag> opFlag = zone.getFlag(FlagTypes.PREVENT_PLAYERS);
        if (opFlag.isEmpty()) {
            return;
        }

        if (opFlag.get().hasPermission(zone, player.uniqueId())) {
            return;
        }

        Zone previousZone = opPreviousZone.get();
        Optional<PreventPlayersFlag> opPreviousFlag = previousZone.getFlag(FlagTypes.PREVENT_PLAYERS);
        if (opPreviousFlag.isEmpty()) {
            return;
        }

        if (opPreviousFlag.get().hasPermission(previousZone, player.uniqueId())) {
            return;
        }

        Optional<Vector3i> opTpPos = getOutsidePosition(zone, player);

        event.setCancelled(true);

        Sponge
                .server()
                .scheduler()
                .submit(Task
                        .builder()
                        .plugin(ZonePlugin.getZonesPlugin().getPluginContainer())
                        .execute(() -> {
                            if (opTpPos.isPresent()) {
                                player.setPosition(opTpPos.get().toDouble());
                                return;
                            }
                            player.setPosition(player
                                    .world()
                                    .properties()
                                    .spawnPosition()
                                    .toDouble());
                        })
                        .delay(Ticks.of(0))
                        .build());

    }

    @Listener
    public void onNear(MoveEntityEvent event, @Getter("entity") Player player) {
        final Location<?, ?> loc = player.location().add(new Vector3i(0, 2, 0));
        int distance = player.get(Keys.VIEW_DISTANCE).map(view -> view * 16).orElse(10);
        Sponge.asyncScheduler().submit(Task.builder().delay(Ticks.of(0)).execute(() -> {
            Map<Vector3i, Zone> zones = ZonePlugin
                    .getZonesPlugin()
                    .getZoneManager()
                    .getRegistered()
                    .stream()
                    .map(zone -> new AbstractMap.SimpleEntry<>(zone
                            .getRegion()
                            .getNearestPosition(loc.blockPosition(), zone.getParentId().isEmpty()),
                            zone))
                    .filter(entry -> entry.getKey().isPresent())
                    .filter(entry -> entry.getKey().get().distance(loc.blockPosition()) <= distance)
                    .filter(entry -> !entry
                            .getValue()
                            .getMembers()
                            .getGroup(player.uniqueId())
                            .contains(GroupKeys.PLAYER_PREVENTION))
                    .collect(Collectors.toMap(entry -> entry.getKey().get(),
                            AbstractMap.SimpleEntry::getValue));
            Sponge
                    .server()
                    .scheduler()
                    .submit(Task
                            .builder()
                            .delay(Ticks.of(0))
                            .execute(() -> zones.forEach((block, zone) -> player.spawnParticles(
                                    ParticleEffect
                                            .builder()
                                            .type(ParticleTypes.BLOCK)
                                            .quantity(1)
                                            .option(ParticleOptions.BLOCK_STATE,
                                                    BlockTypes.BARRIER.get().defaultState())
                                            .option(ParticleOptions.VELOCITY,
                                                    new Vector3d(0, 0.1, 0))
                                            .build(),
                                    block.toDouble())))
                            .plugin(ZonePlugin.getZonesPlugin().getPluginContainer())
                            .build());
        }).plugin(ZonePlugin.getZonesPlugin().getPluginContainer()).build());
    }

    public static Optional<Vector3i> getOutsidePosition(
            @NotNull Zone zoneOne, @NotNull Locatable player) {
        return zoneOne.getRegion().getTrueChildren().stream().filter(boundedRegion -> {
            Vector3i position = boundedRegion.getMin().add(-1, 0, -1);
            position = player.world().highestPositionAt(position);
            final Vector3i finalPosition = position;
            //check if in any zone
            return ZonePlugin
                    .getZonesPlugin()
                    .getZoneManager()
                    .getRegistered()
                    .stream()
                    .noneMatch(zone -> zone.inRegion(player.world(), finalPosition.toDouble()));
        }).findAny().map(boundedRegion -> {
            Vector3i position = boundedRegion.getMin().add(-1, 0, -1);
            return player.world().highestPositionAt(position);
        });
    }

}