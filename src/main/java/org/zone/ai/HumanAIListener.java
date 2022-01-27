package org.zone.ai;

import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.ai.goal.builtin.creature.RandomWalkingGoal;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.world.chunk.ChunkEvent;
import org.spongepowered.api.util.AABB;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3d;
import org.zone.ZonePlugin;
import org.zone.keys.ZoneKeys;
import org.zone.region.Zone;
import org.zone.region.bounds.BoundedRegion;

import java.util.Optional;

public class HumanAIListener {

    @Listener
    public void onChunkSpawn(ChunkEvent.Load event) {
        AABB aabb = AABB.of(event.chunk().min(), event.chunk().max());
        ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getZonesIntersecting(aabb)
                .parallelStream()
                .filter(zone -> zone.getParent().isPresent())
                .filter(zone -> zone
                        .getEntities()
                        .stream()
                        .noneMatch(entity -> entity instanceof Human))
                .forEach(zone -> {
                    //spawn rate
                    BoundedRegion region = zone.getRegion().getTrueChildren().iterator().next();
                    World<?, ?> world = event.chunk().world();
                    Human human = world.createEntity(EntityTypes.HUMAN, region.getCenter());
                    RandomWalkingGoal.builder().executionChance(100).speed(0.1).build(human);
                    world.spawnEntity(human);
                });

    }

    @Listener
    public void onMove(MoveEntityEvent event, @Getter("entity") Human human) {
        Optional<String> opZoneId = human.get(ZoneKeys.HUMAN_AI_ATTACHED_ZONE_ID);
        if (opZoneId.isEmpty()) {
            return;
        }
        Optional<Zone> opNextZone = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getPriorityZone(human.location());
        if (opNextZone.isEmpty()) {
            return;
        }
        Zone zone = opNextZone.get();
        if (zone.getId().equals(opZoneId.get())) {
            return;
        }
        event.setCancelled(true);
        Vector3d currentVel = human.velocity().get();
        Vector3d invertedVel = new Vector3d(-currentVel.x(), -currentVel.y(), -currentVel.z());
        human.velocity().set(invertedVel);
    }
}
