package org.zone.command;

import org.spongepowered.api.command.Command;
import org.zone.Permissions;
import org.zone.command.create.RegionCreateEnd;
import org.zone.command.create.RegionCreateStart;
import org.zone.command.create.sub.SubRegionCreateStart;
import org.zone.command.zone.flags.interact.door.SetGroupDoorInteractionFlagCommand;
import org.zone.command.zone.flags.interact.door.SetValueDoorInteractionFlagCommand;
import org.zone.command.zone.flags.interact.door.ViewDoorInteractionFlagCommand;
import org.zone.command.zone.flags.members.AddMemberGroupCommand;
import org.zone.command.zone.flags.members.ViewMemberGroupCommand;
import org.zone.command.zone.info.ShowCommand;

@SuppressWarnings("NonThreadSafeLazyInitialization")
public final class ZoneCommands {

    private static Command.Parameterized createRegionBounds;
    private static Command.Parameterized createSubRegionBounds;
    private static Command.Parameterized endRegionBounds;
    private static Command.Parameterized viewMemberGroup;
    private static Command.Parameterized addMemberGroup;
    private static Command.Parameterized doorInteractionValueSet;
    private static Command.Parameterized doorInteractionGroupSet;
    private static Command.Parameterized doorInteractionView;
    private static Command.Parameterized showZone;

    private ZoneCommands() {
        throw new RuntimeException("Should not init");
    }

    public static Command.Parameterized getDoorInteractionView() {
        if (doorInteractionView==null) {
            doorInteractionView =
                    Command
                            .builder()
                            .addParameter(ViewDoorInteractionFlagCommand.ZONE)
                            .executor(new ViewDoorInteractionFlagCommand.Executor())
                            .build();
        }
        return doorInteractionView;
    }

    public static Command.Parameterized getDoorInteractionValueGroup() {
        if (doorInteractionGroupSet==null) {
            doorInteractionGroupSet =
                    Command
                            .builder()
                            .addParameters(SetGroupDoorInteractionFlagCommand.ZONE,
                                    SetGroupDoorInteractionFlagCommand.VALUE)
                            .executor(new SetGroupDoorInteractionFlagCommand.Executor())
                            .build();
        }
        return doorInteractionGroupSet;
    }

    public static Command.Parameterized getDoorInteractionValueSet() {
        if (doorInteractionValueSet==null) {
            doorInteractionValueSet =
                    Command
                            .builder()
                            .addParameters(SetValueDoorInteractionFlagCommand.ZONE,
                                    SetValueDoorInteractionFlagCommand.VALUE)
                            .executor(new SetValueDoorInteractionFlagCommand.Executor())
                            .build();
        }
        return doorInteractionValueSet;
    }

    public static Command.Parameterized getShowZone() {
        if (showZone==null) {
            showZone =
                    Command
                            .builder()
                            .addParameter(ShowCommand.ZONE)
                            .executor(new ShowCommand.Executor())
                            .build();
        }
        return showZone;
    }

    public static Command.Parameterized getAddMemberGroup() {
        if (addMemberGroup==null) {
            addMemberGroup =
                    Command
                            .builder()
                            .addParameters(
                                    AddMemberGroupCommand.ZONE,
                                    AddMemberGroupCommand.GROUP,
                                    AddMemberGroupCommand.USER)
                            .executor(new AddMemberGroupCommand.Executor())
                            .build();
        }
        return addMemberGroup;
    }

    public static Command.Parameterized getCreateRegionBounds() {
        if (createRegionBounds==null) {
            createRegionBounds = Command
                    .builder()
                    .permission(Permissions.REGION_CREATE_BOUNDS.getPermission())
                    .addParameter(RegionCreateStart.NAME_PARAMETER)
                    .executor(new RegionCreateStart.Executor())
                    .build();
        }
        return createRegionBounds;
    }

    public static Command.Parameterized getCreateSubRegionBounds() {
        if (createSubRegionBounds==null) {
            createSubRegionBounds = Command
                    .builder()
                    .permission(Permissions.REGION_CREATE_BOUNDS.getPermission())
                    .addParameters(SubRegionCreateStart.ZONE, SubRegionCreateStart.NAME)
                    .executor(new SubRegionCreateStart.Executor())
                    .build();
        }
        return createSubRegionBounds;
    }

    public static Command.Parameterized getEndRegionBounds() {
        if (endRegionBounds==null) {
            endRegionBounds =
                    Command
                            .builder()
                            .permission(Permissions.REGION_CREATE_BOUNDS.getPermission())
                            .executor(new RegionCreateEnd.Executor())
                            .build();
        }
        return endRegionBounds;
    }

    public static Command.Parameterized getViewMemberGroup() {
        if (viewMemberGroup==null) {
            viewMemberGroup = Command.builder().executor(new ViewMemberGroupCommand.Executor())
                    .addParameters(ViewMemberGroupCommand.ZONE, ViewMemberGroupCommand.GROUP, ViewMemberGroupCommand.PAGE)
                    .build();
        }
        return viewMemberGroup;
    }

    private static Command.Parameterized createRegionCommand() {
        Command.Parameterized createSubCommand =
                Command
                        .builder()
                        .addChild(getCreateSubRegionBounds(), "bounds")
                        .build();

        return Command
                .builder()
                .addChild(getCreateRegionBounds(), "bounds")
                .addChild(getEndRegionBounds(), "end")
                .addChild(createSubCommand, "sub", "house")
                .build();
    }

    private static Command.Parameterized createSetInteractionCommand() {
        Command.Parameterized setDoorInteraction = Command
                .builder()
                .addChild(getDoorInteractionValueSet(), "enabled")
                .addChild(getDoorInteractionValueGroup(), "group")
                .build();

        return Command
                .builder()
                .addChild(setDoorInteraction, "door")
                .build();
    }

    public static Command.Parameterized createViewInteractionCommand() {
        return Command
                .builder()
                .addChild(getDoorInteractionView(), "door")
                .build();
    }


    private static Command.Parameterized createZoneMetaCommand() {
        Command.Parameterized viewCommand = Command
                .builder()
                .addChild(getViewMemberGroup(), "members", "member")
                .addChild(createViewInteractionCommand(), "interation", "interact")
                .build();

        Command.Parameterized addCommand = Command
                .builder()
                .addChild(getAddMemberGroup(), "member")
                .build();

        Command.Parameterized setCommand = Command
                .builder()
                .addChild(createSetInteractionCommand(), "interaction", "interact")
                .build();

        return Command
                .builder()
                .addChild(addCommand, "add")
                .addChild(viewCommand, "view")
                .addChild(getShowZone(), "show")
                .addChild(setCommand, "set", "apply")
                .build();
    }

    public static Command.Parameterized createZoneCommand() {

        return Command.builder()
                .addChild(createRegionCommand(), "create")
                .addChild(createZoneMetaCommand(), "zone")
                .build();
    }

}
