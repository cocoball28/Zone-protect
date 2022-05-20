package command.info;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.spongepowered.api.command.CommandResult;
import org.zone.commands.structure.ZoneCommands;
import org.zone.region.ZoneManager;
import tools.CommandAssert;

public class TestInfoCommand {

    @Test
    public void testInfoCommand() {
        //setup
        MockedStatic<CommandResult> staticResult = Mockito.mockStatic(CommandResult.class);
        CommandResult successResult = Mockito.mock(CommandResult.class);
        staticResult.when(CommandResult::success).thenReturn(successResult);

        ZoneManager manager = new ZoneManager();

        //test
        CommandResult result = CommandAssert.test(mcb -> Mockito
                .when(mcb.plugin.getZoneManager())
                .thenReturn(manager), ZoneCommands.ZONE_PLUGIN_INFO_COMMAND, "info");

        //compare
        Assertions.assertEquals(successResult, result);

        //end
        staticResult.close();
    }
}
