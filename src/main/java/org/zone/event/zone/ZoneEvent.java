package org.zone.event.zone;

import org.zone.event.ZonesEvent;
import org.zone.region.Zone;

public interface ZoneEvent extends ZonesEvent {

    Zone getZone();
}
