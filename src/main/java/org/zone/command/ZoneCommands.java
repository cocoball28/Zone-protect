package org.zone.command;

import org.spongepowered.api.command.Command;
import org.zone.Permissions;
import org.zone.command.create.RegionCreateEnd;
import org.zone.command.create.RegionCreateStart;
import org.zone.command.create.sub.SubRegionCreateStart;
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
    private static Command.Parameterized showZone;

    private ZoneCommands() {
        throw new RuntimeException("Should not init");
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

    public static Command.Parameterized createZoneCommand() {
        Command.Parameterized createSubCommand =
                Command
                        .builder()
                        .addChild(getCreateSubRegionBounds(), "bounds")
                        .build();

        Command.Parameterized createCommand =
                Command
                        .builder()
                        .addChild(getCreateRegionBounds(), "bounds")
                        .addChild(getEndRegionBounds(), "end")
                        .addChild(createSubCommand, "sub", "house")
                        .build();

        Command.Parameterized viewCommand =
                Command.builder().addChild(getViewMemberGroup(), "members", "member").build();

        Command.Parameterized addCommand = Command.builder().addChild(getAddMemberGroup(), "member").build();

        Command.Parameterized zoneSpecificCommand = Command
                .builder()
                .addChild(addCommand, "add")
                .addChild(viewCommand, "view")
                .addChild(getShowZone(), "show")
                .build();

        return Command.builder()
                .addChild(createCommand, "create")
                .addChild(zoneSpecificCommand, "zone")
                .build();
    }

}
