package org.zone.command;

import org.spongepowered.api.command.Command;
import org.zone.Permissions;
import org.zone.command.create.RegionCreateStart;

public final class ZoneCommands {

    public static final Command.Parameterized CREATE_REGION_BOUNDS = Command
            .builder()
            .permission(Permissions.REGION_CREATE_BOUNDS.getPermission())
            .addParameter(RegionCreateStart.NAME_PARAMETER)
            .executor(new RegionCreateStart.Executor())
            .build();

    public static Command.Parameterized createZoneCommand() {
        Command.Parameterized createCommand = Command.builder().addChild(CREATE_REGION_BOUNDS, "bounds").build();


        return Command.builder()
                .addChild(createCommand, "create")
                .build();
    }

}
