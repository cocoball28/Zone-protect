package org.zone.ai;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.ai.goal.builtin.creature.RandomWalkingGoal;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.CollideBlockEvent;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSources;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Ticks;
import org.spongepowered.api.world.HeightTypes;
import org.spongepowered.api.world.World;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3i;
import org.zone.ZonePlugin;
import org.zone.keys.ZoneKeys;
import org.zone.region.Zone;

import java.util.Optional;

public class HumanAIListener {

    @Listener
    public void onChunkSpawn(MoveEntityEvent event, @Getter("entity") Player player) {
        Optional<Zone> opZone = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getNearestZoneInView(player);
        if (opZone.isEmpty()) {
            return;
        }
        Zone zone = opZone.get();
        World<?, ?> world = player.world();
        Vector3i height = world.height(HeightTypes.MOTION_BLOCKING_NO_LEAVES.get(),
                player.blockPosition());
        if (zone
                .getEntities(height.y() - 2, world.height())
                .stream()
                .anyMatch(entity -> entity instanceof Human)) {
            return;
        }
        @NotNull Vector3i spawnPos = zone
                .getRegion()
                .getNearestPosition(player.blockPosition())
                .orElseGet(() -> zone.getRegion().getTrueChildren().iterator().next().getCenter());

        Human human = world.createEntity(EntityTypes.HUMAN, spawnPos);
        human.offer(ZoneKeys.HUMAN_AI_ATTACHED_ZONE_ID, zone.getId());
        RandomWalkingGoal.builder().executionChance(100).speed(0.1).build(human);
        world.spawnEntity(human);
    }

    @Listener
    public void onDamage(DamageEntityEvent event, @Getter("entity") Human human) {
        Optional<String> opZoneId = human.get(ZoneKeys.HUMAN_AI_ATTACHED_ZONE_ID);
        if (opZoneId.isEmpty()) {
            return;
        }
        event.setCancelled(true);
    }

    @Listener
    public void onMoveInside(CollideBlockEvent.Inside event, @First Human human) {
        Optional<String> opZoneId = human.get(ZoneKeys.HUMAN_AI_ATTACHED_ZONE_ID);
        if (opZoneId.isEmpty()) {
            return;
        }
        event.setCancelled(true);
    }

    @Listener
    public void onMoveStepOn(CollideBlockEvent.StepOn event, @First Human human) {
        Optional<String> opZoneId = human.get(ZoneKeys.HUMAN_AI_ATTACHED_ZONE_ID);
        if (opZoneId.isEmpty()) {
            return;
        }
        event.setCancelled(true);
    }


    //humans dont have proper inventories, this wont trigger
    /*@Listener
    public void onItemPickup(ChangeInventoryEvent.Pickup.Pre event) {
        Inventory inv = event.inventory();
        if (!(inv instanceof EquipmentInventory eInv)) {
            return;
        }
        Optional<Equipable> opCarrier = eInv.carrier();
        if (opCarrier.isEmpty()) {
            return;
        }
        if (!(opCarrier.get() instanceof Human human)) {
            return;
        }
        Optional<String> opZoneId = human.get(ZoneKeys.HUMAN_AI_ATTACHED_ZONE_ID);
        if (opZoneId.isEmpty()) {
            return;
        }
        event.setCancelled(true);
    }*/

    @Listener
    public void onMove(MoveEntityEvent event, @Getter("entity") Human human) {
        Optional<String> opZoneId = human.get(ZoneKeys.HUMAN_AI_ATTACHED_ZONE_ID);
        if (opZoneId.isEmpty()) {
            return;
        }
        Optional<Zone> opCurrentZone = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getPriorityZone(human.world(), event.originalPosition());
        if (opCurrentZone.isEmpty()) {
            Sponge.server().scheduler().submit(Task.builder().execute(() -> {
                Optional<Zone> opAssignedZone = ZonePlugin
                        .getZonesPlugin()
                        .getZoneManager()
                        .getZone(opZoneId.get());
                if (opAssignedZone.isEmpty()) {
                    human.damage(human.get(Keys.MAX_HEALTH).orElse(20.0), DamageSources.GENERIC);
                    return;
                }
                Optional<Vector3i> opTel = opAssignedZone
                        .get()
                        .getRegion()
                        .getNearestPosition(human.blockPosition(), true);
                if (opTel.isEmpty()) {
                    human.damage(human.get(Keys.MAX_HEALTH).orElse(20.0), DamageSources.GENERIC);
                    return;
                }
                Vector3i tel = human
                        .world()
                        .height(HeightTypes.MOTION_BLOCKING_NO_LEAVES.get(), opTel.get());
                human.setPosition(tel.toDouble());
            }).plugin(ZonePlugin.getZonesPlugin().getPluginContainer()).delay(Ticks.of(0)).build());
        }


        Optional<Zone> opNextZone = ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getPriorityZone(human.world(), event.destinationPosition());
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
