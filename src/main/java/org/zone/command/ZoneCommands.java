package org.zone.command;

import org.spongepowered.api.command.Command;
import org.zone.Permissions;
import org.zone.command.create.RegionCreateEnd;
import org.zone.command.create.RegionCreateStart;

@SuppressWarnings("NonThreadSafeLazyInitialization")
public final class ZoneCommands {

    private static Command.Parameterized createRegionBounds;
    private static Command.Parameterized endRegionBounds;

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

    public static Command.Parameterized createZoneCommand() {
        Command.Parameterized createCommand =
                Command
                        .builder()
                        .addChild(getCreateRegionBounds(), "bounds")
                        .addChild(getEndRegionBounds(), "end")
                        .build();
        return Command.builder()
                .addChild(createCommand, "create")
                .build();
    }

}
