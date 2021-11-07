package org.zone.event.listener;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.math.vector.Vector3i;
import org.zone.player.PlayerKeys;
import org.zone.region.ZoneBuilder;
import org.zone.region.regions.BoundedRegion;
import org.zone.region.regions.Region;
import org.zone.region.regions.type.PointRegion;

import java.util.Optional;
import java.util.function.Consumer;

public class PlayerListener {

    @Listener
    public void onPlayerRegionCreateMove(MoveEntityEvent event, @Getter("entity") Player player) {
        Optional<ZoneBuilder> opRegionBuilder = player.get(PlayerKeys.REGION_BUILDER);
        if (opRegionBuilder.isEmpty()) {
            return;
        }
        ZoneBuilder regionBuilder = opRegionBuilder.get();
        Region region = regionBuilder.getRegion();
        if (!(region instanceof PointRegion r)) {
            return;
        }

        this.runOnOutside(r, (int) (event.originalPosition().y() - 1), player::resetBlockChange);
        r.setPointTwo(player.location().blockPosition());
        this.runOnOutside(r, (int) (event.originalPosition().y() - 1), vector -> player.sendBlockChange(vector,
                BlockTypes.ORANGE_WOOL.get().defaultState()));

    }

    private void runOnOutside(BoundedRegion region, int y, Consumer<? super Vector3i> consumer) {
        Vector3i min = region.getMin();
        Vector3i max = region.getMax();
        for (int x = min.x(); x < max.x(); x++) {
            for (int z = min.z(); z < max.z(); z++) {
                if (min.z()==z) {
                    consumer.accept(new Vector3i(x, y, z));
                    continue;
                }
                if (max.z()==(z - 1)) {
                    consumer.accept(new Vector3i(x, y, z));
                }
            }
        }
    }
}
