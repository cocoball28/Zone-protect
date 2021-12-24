package org.zone.region.flag;

import org.zone.region.flag.greetings.GreetingsFlagType;
import org.zone.region.flag.interact.block.destroy.BlockBreakFlagType;
import org.zone.region.flag.interact.block.place.BlockPlaceFlagType;
import org.zone.region.flag.interact.door.DoorInteractionFlagType;
import org.zone.region.flag.meta.eco.EcoFlagType;
import org.zone.region.flag.meta.edit.EditingFlagType;
import org.zone.region.flag.meta.member.MembersFlagType;
import org.zone.region.flag.move.monster.PreventMonsterFlagType;
import org.zone.region.flag.move.player.PreventPlayersFlagType;

/**
 * All known default flag types found within the zones plugin
 */
public final class FlagTypes {

    public static final PreventMonsterFlagType PREVENT_MONSTER = new PreventMonsterFlagType();
    public static final MembersFlagType MEMBERS = new MembersFlagType();
    public static final DoorInteractionFlagType DOOR_INTERACTION = new DoorInteractionFlagType();
    public static final BlockBreakFlagType BLOCK_BREAK = new BlockBreakFlagType();
    public static final EcoFlagType ECO = new EcoFlagType();
    public static final EditingFlagType EDITING = new EditingFlagType();
    public static final BlockPlaceFlagType BLOCK_PLACE = new BlockPlaceFlagType();
    public static final GreetingsFlagType GREETINGS_FLAG_TYPE = new GreetingsFlagType();
    public static final PreventPlayersFlagType PREVENT_PLAYERS_FLAG_TYPE = new PreventPlayersFlagType();

    private FlagTypes() {
        throw new RuntimeException("Should not init");
    }
}
