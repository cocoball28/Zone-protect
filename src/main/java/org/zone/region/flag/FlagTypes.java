package org.zone.region.flag;

import org.zone.region.flag.greetings.GreetingsFlagType;
import org.zone.region.flag.interact.block.destroy.BlockBreakFlagType;
import org.zone.region.flag.interact.door.DoorInteractionFlagType;
import org.zone.region.flag.meta.MembersFlagType;
import org.zone.region.flag.move.monster.PreventMonsterFlagType;

public final class FlagTypes {

    public static final PreventMonsterFlagType PREVENT_MONSTER = new PreventMonsterFlagType();
    public static final MembersFlagType MEMBERS = new MembersFlagType();
    public static final DoorInteractionFlagType DOOR_INTERACTION = new DoorInteractionFlagType();
    public static final BlockBreakFlagType BLOCK_BREAK = new BlockBreakFlagType();
    public static final GreetingsFlagType GREETINGS_FLAG_TYPE = new GreetingsFlagType();

    private FlagTypes() {
        throw new RuntimeException("Should not init");
    }
}
