package org.zone.region.flag.meta.request.visibility;

import org.jetbrains.annotations.NotNull;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;

public class ZoneVisibilityFlag implements Flag {

    private @NotNull ZoneVisibility zoneVisibility = ZoneVisibility.PRIVATE;

    public @NotNull ZoneVisibility getZoneVisibility() {
        return this.zoneVisibility;
    }

    public void setZoneVisibility(@NotNull ZoneVisibility zoneVisibility) {
        this.zoneVisibility = zoneVisibility;
    }

    @Override
    public @NotNull FlagType<?> getType() {
        return FlagTypes.ZONE_VISIBILITY;
    }
}
