package org.zone.region.flag.meta.request.visibility;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagTypes;

public class ZoneVisibilityFlag implements Flag.Serializable {

    private @NotNull ZoneVisibility zoneVisibility = ZoneVisibility.PRIVATE;

    /**
     * Gets the visibility of the zone. By default, it is {@link ZoneVisibility#PRIVATE}
     *
     * @return The visibility of the zone
     * @since 1.0.1
     */
    public @NotNull ZoneVisibility getZoneVisibility() {
        return this.zoneVisibility;
    }

    /**
     * Sets the visibility of the zone
     *
     * @param zoneVisibility The visibility to set
     * @since 1.0.1
     */
    public void setZoneVisibility(@NotNull ZoneVisibility zoneVisibility) {
        this.zoneVisibility = zoneVisibility;
    }

    @Override
    public @NotNull ZoneVisibilityFlagType getType() {
        return FlagTypes.ZONE_VISIBILITY;
    }
}
