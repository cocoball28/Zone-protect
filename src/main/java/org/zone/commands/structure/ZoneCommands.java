package org.zone.commands.structure;

import org.zone.commands.structure.create.ZoneCreateEndCommand;
import org.zone.commands.structure.create.ZoneCreateStartCommand;
import org.zone.commands.structure.create.ZoneCreateSubStartCommand;
import org.zone.commands.structure.zone.flags.eco.ZoneViewBalanceCommand;
import org.zone.commands.structure.zone.flags.greetings.SetGreetingsMessageCommand;
import org.zone.commands.structure.zone.flags.interact.destroy.ZoneFlagBlockBreakSetEnabledCommand;
import org.zone.commands.structure.zone.flags.interact.destroy.ZoneFlagBlockBreakSetGroupCommand;
import org.zone.commands.structure.zone.flags.interact.destroy.ZoneFlagBlockBreakViewCommand;
import org.zone.commands.structure.zone.flags.interact.door.ZoneFlagInteractDoorEnabledCommand;
import org.zone.commands.structure.zone.flags.interact.door.ZoneFlagInteractDoorGroupCommand;
import org.zone.commands.structure.zone.flags.interact.door.ZoneFlagInteractDoorViewCommand;
import org.zone.commands.structure.zone.flags.interact.place.ZoneFlagBlockPlaceSetEnabledCommand;
import org.zone.commands.structure.zone.flags.interact.place.ZoneFlagBlockPlaceSetGroupCommand;
import org.zone.commands.structure.zone.flags.interact.place.ZoneFlagBlockPlaceViewCommand;
import org.zone.commands.structure.zone.flags.members.ZoneFlagMemberGroupAddCommand;
import org.zone.commands.structure.zone.flags.members.ZoneFlagMemberGroupViewCommand;
import org.zone.commands.structure.zone.info.bounds.ZoneInfoBoundsShowCommand;
import org.zone.commands.system.ArgumentCommand;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A list of all known commands within the Zones plugin
 */
@SuppressWarnings("unused")
public interface ZoneCommands {

    ZoneCreateEndCommand ZONE_CREATE_END = new ZoneCreateEndCommand();
    ZoneCreateStartCommand ZONE_CREATE_START_COMMAND = new ZoneCreateStartCommand();
    ZoneCreateSubStartCommand ZONE_CREATE_SUB_START_COMMAND = new ZoneCreateSubStartCommand();
    ZoneInfoBoundsShowCommand ZONE_INFO_BOUNDS_SHOW_COMMAND = new ZoneInfoBoundsShowCommand();
    ZoneFlagInteractDoorEnabledCommand ZONE_FLAG_INTERACT_DOOR_ENABLED_COMMAND = new ZoneFlagInteractDoorEnabledCommand();
    ZoneFlagInteractDoorGroupCommand ZONE_FLAG_INTERACT_DOOR_GROUP_COMMAND = new ZoneFlagInteractDoorGroupCommand();
    ZoneFlagInteractDoorViewCommand ZONE_FLAG_INTERACT_DOOR_VIEW_COMMAND = new ZoneFlagInteractDoorViewCommand();
    ZoneFlagMemberGroupAddCommand ZONE_FLAG_MEMBER_GROUP_ADD_COMMAND = new ZoneFlagMemberGroupAddCommand();
    ZoneFlagMemberGroupViewCommand ZONE_FLAG_MEMBER_GROUP_VIEW_COMMAND = new ZoneFlagMemberGroupViewCommand();
    ZoneFlagBlockBreakSetEnabledCommand ZONE_FLAG_BLOCK_BREAK_SET_ENABLED_COMMAND = new ZoneFlagBlockBreakSetEnabledCommand();
    ZoneFlagBlockBreakViewCommand ZONE_FLAG_BLOCK_BREAK_VIEW_COMMAND = new ZoneFlagBlockBreakViewCommand();
    ZoneFlagBlockBreakSetGroupCommand ZONE_FLAG_BLOCK_BREAK_SET_GROUP_COMMAND = new ZoneFlagBlockBreakSetGroupCommand();
    ZoneFlagBlockPlaceSetEnabledCommand ZONE_FLAG_BLOCK_PLACE_SET_ENABLED_COMMAND = new ZoneFlagBlockPlaceSetEnabledCommand();
    ZoneFlagBlockPlaceViewCommand ZONE_FLAG_BLOCK_PLACE_VIEW_COMMAND = new ZoneFlagBlockPlaceViewCommand();
    ZoneFlagBlockPlaceSetGroupCommand ZONE_FLAG_BLOCK_PLACE_SET_GROUP_COMMAND = new ZoneFlagBlockPlaceSetGroupCommand();
    ZoneViewBalanceCommand ZONE_FLAG_VIEW_BALANCE_COMMAND = new ZoneViewBalanceCommand();
    SetGreetingsMessageCommand SET_GREETINGS_MESSAGE_COMMAND = new SetGreetingsMessageCommand();

    static ZoneSpongeCommand createCommand() {
        Collection<ArgumentCommand> collection = Arrays
                .stream(ZoneCommands.class.getDeclaredFields())
                .parallel()
                .filter(field -> ArgumentCommand.class.isAssignableFrom(field.getType()))
                .map(field -> {
                    try {
                        return (ArgumentCommand) field.get(null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        //noinspection ReturnOfNull
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return new ZoneSpongeCommand(collection);
    }
}
