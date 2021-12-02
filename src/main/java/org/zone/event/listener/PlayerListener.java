package org.zone.event.listener;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.transaction.BlockTransaction;
import org.spongepowered.api.block.transaction.Operations;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.tag.BlockTypeTags;
import org.spongepowered.api.world.server.ServerLocation;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3i;
import org.zone.Permissions;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.ZoneBuilder;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.interact.block.destroy.BlockBreakFlag;
import org.zone.region.flag.interact.door.DoorInteractionFlag;
import org.zone.region.group.Group;
import org.zone.region.group.SimpleGroup;
import org.zone.region.regions.BoundedRegion;
import org.zone.region.regions.Region;
import org.zone.region.regions.type.PointRegion;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayerListener {

    @Listener
    public void onPlayerBlockChangeEvent(ChangeBlockEvent.All event, @Root Player player) {
        Map<BlockTransaction, Zone> inZone =
                event
                        .transactions()
                        .stream()
                        .filter(t -> t.original().location().isPresent())
                        .map(t -> {
                            ServerLocation loc = t.original().location().orElseThrow(() -> new RuntimeException("Broke logic"));
                            @NotNull Optional<Zone> zone = ZonePlugin.getZonesPlugin().getZoneManager().getPriorityZone(loc);
                            return new AbstractMap.SimpleEntry<>(t, zone.orElse(null));
                        })
                        .filter(map -> map.getValue()!=null)
                        .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey,
                                AbstractMap.SimpleEntry::getValue));

        Stream<Map.Entry<BlockTransaction, Zone>> blockBreaks =
                inZone.entrySet().stream().filter(entry -> entry.getKey().operation()==Operations.BREAK.get());

        Set<BlockTransaction> unbrokenBlocks = blockBreaks
                .filter(entry -> entry.getValue().getFlag(FlagTypes.BLOCK_BREAK).isPresent())
                .map(entry -> {
                    Group group = entry.getValue().getMembers().getGroup(player.uniqueId());
                    BlockBreakFlag blockBreakFlag =
                            entry
                                    .getValue()
                                    .getFlag(FlagTypes.BLOCK_BREAK)
                                    .orElseThrow(() -> new RuntimeException("Broke logic"));
                    Group flagGroup = blockBreakFlag.getGroup(entry.getValue().getMembers()).orElse(null);
                    if (flagGroup==null) {
                        //noinspection ReturnOfNull
                        return null;
                    }
                    if (group.inherits(flagGroup)) {
                        //noinspection ReturnOfNull
                        return null;
                    }
                    return entry.getKey();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        inZone = inZone
                .entrySet()
                .stream()
                .filter(entry -> unbrokenBlocks.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


        inZone.forEach((block, zone) -> block.invalidate());
    }

    @Listener
    public void onPlayerInteractSecondary(InteractBlockEvent.Secondary event, @Root Player player) {
        BlockSnapshot snapshot = event.block();
        @NotNull Optional<Zone> opZone = ZonePlugin.getZonesPlugin().getZoneManager().getPriorityZone(player.world(),
                event.interactionPoint());
        if (opZone.isEmpty()) {
            return;
        }
        Zone zone = opZone.get();
        Group playerGroup = zone.getMembers().getGroup(player.uniqueId());
        if (BlockTypeTags.DOORS.get().contains(snapshot.state().type())) {
            DoorInteractionFlag flag = zone.getFlag(FlagTypes.DOOR_INTERACTION).orElse(DoorInteractionFlag.ELSE);
            if (!flag.getValue().orElse(false)) {
                return;
            }
            if (player instanceof ServerPlayer sPlayer && sPlayer.hasPermission(Permissions.BYPASS_DOOR_INTERACTION.getPermission())) {
                return;
            }
            if (zone
                    .getParentId()
                    .isPresent()
                    && zone
                    .getParent()
                    .map(parent -> parent
                            .getMembers()
                            .getGroup(player.uniqueId())
                            .equals(SimpleGroup.OWNER))
                    .orElse(false)) {
                return;
            }
            if (!flag.hasPermission(zone.getMembers(), playerGroup)) {
                event.setCancelled(true);
            }
            return;
        }
    }

    @Listener
    public void onPlayerRegionCreateMove(MoveEntityEvent event, @Getter("entity") Player player) {
        if (event.originalPosition().toInt().equals(event.destinationPosition().toInt())) {
            return;
        }

        Optional<ZoneBuilder> opRegionBuilder =
                ZonePlugin.getZonesPlugin().getMemoryHolder().getZoneBuilder(player.uniqueId());
        if (opRegionBuilder.isEmpty()) {
            return;
        }
        ZoneBuilder regionBuilder = opRegionBuilder.get();
        Region region = regionBuilder.getRegion();
        if (!(region instanceof PointRegion r)) {
            return;
        }

        r.setPointTwo(event.destinationPosition().toInt());
        runOnOutside(r, (int) (event.destinationPosition().y() + 3), vector ->
                        player.spawnParticles(ParticleEffect
                                        .builder()
                                        .velocity(new Vector3d(0, 0, 0))
                                        .type(ParticleTypes.SMOKE)
                                        .scale(2.0)
                                        .build(),
                                vector.toDouble()),
                regionBuilder.getParentId()!=null);

    }

    public static void runOnOutside(BoundedRegion region, int y, Consumer<? super Vector3i> consumer,
                                    boolean showHeight) {
        Vector3i min = region.getMin();
        Vector3i max = region.getMax();
        for (int x = min.x(); x <= max.x(); x++) {
            for (int z = min.z(); z <= max.z(); z++) {
                if (min.z()==z) {
                    consumer.accept(new Vector3i(x, y, z));
                }
                if (min.x()==x) {
                    consumer.accept(new Vector3i(x, y, z));
                }
                if (max.z()==z) {
                    consumer.accept(new Vector3i(x, y, z));
                }
                if (max.x()==x) {
                    consumer.accept(new Vector3i(x, y, z));
                }
                if (showHeight) {
                    for (int usingY = min.y(); usingY <= max.y(); usingY++) {
                        if (min.y()==usingY && (min.x()==x || min.z()==z)) {
                            consumer.accept(new Vector3i(x, usingY, z));
                        }
                        if (max.y()==usingY && (max.x()==x || max.z()==z)) {
                            consumer.accept(new Vector3i(x, usingY, z));
                        }
                    }
                }
            }
        }
    }
}
