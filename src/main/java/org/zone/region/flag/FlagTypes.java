package org.zone.region.flag;

import org.zone.region.flag.interact.door.DoorInteractionFlagType;
import org.zone.region.flag.meta.MembersFlagType;
import org.zone.region.flag.move.monster.PreventMonsterFlagType;

public final class FlagTypes {

    public static final PreventMonsterFlagType PREVENT_MONSTER = new PreventMonsterFlagType();
    public static final MembersFlagType MEMBERS = new MembersFlagType();
    public static final DoorInteractionFlagType DOOR_INTERACTION = new DoorInteractionFlagType();

    private FlagTypes() {
        throw new RuntimeException("Should not init");
    }
}
