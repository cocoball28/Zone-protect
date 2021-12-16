package org.zone.region.flag.meta.edit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.math.vector.Vector3i;
import org.zone.region.bounds.BoundedRegion;
import org.zone.region.bounds.PositionType;
import org.zone.region.bounds.mode.BoundMode;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;

import java.util.Optional;
import java.util.UUID;

public class EditingFlag implements Flag {

    private final @Nullable UUID playerEditing;
    private final @NotNull PositionType positionType;
    private final @NotNull BoundedRegion region;
    private final @NotNull BoundMode boundMode;
    private @NotNull Vector3i newPosition;

    public EditingFlag(@NotNull BoundedRegion region, @NotNull PositionType positionType, @NotNull Vector3i newPosition, @NotNull BoundMode boundMode, @Nullable UUID uuid) {
        this.playerEditing = uuid;
        this.positionType = positionType;
        this.newPosition = newPosition;
        this.region = region;
        this.boundMode = boundMode;
    }
    
    public @NotNull BoundMode getBoundMode() {
        return this.boundMode;
    }

    public @NotNull BoundedRegion getRegion() {
        return this.region;
    }

    public Optional<UUID> getPlayer() {
        return Optional.ofNullable(this.playerEditing);
    }

    public @NotNull PositionType getPositionType() {
        return this.positionType;
    }

    public @NotNull Vector3i getNewPosition() {
        return this.newPosition;
    }

    public void setNewPosition(@NotNull Vector3i vector3i) {
        this.newPosition = vector3i;
    }

    @Override
    public @NotNull FlagType<?> getType() {
        return FlagTypes.EDITING;
    }
}
