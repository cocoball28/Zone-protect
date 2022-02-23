package region.flag.meta.request.visibility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.zone.region.flag.meta.request.visibility.ZoneVisibility;
import org.zone.region.flag.meta.request.visibility.ZoneVisibilityFlag;
import tools.configuration.FlagLoadTester;

import java.io.IOException;

public class ZoneVisibilityFlagTests {

    @Test
    public void testVisibilitySaveNull() {
        ZoneVisibilityFlag flag = new ZoneVisibilityFlag();
        flag.setZoneVisibility(ZoneVisibility.PRIVATE);


        try {
            if (!FlagLoadTester.testNull(flag)) {
                Assertions.fail("Did not remove children");
            }
        } catch (IOException e) {
            Assertions.fail("Could not save", e);
        }
    }

    @Test
    public void testVisibilitySave() {
        ZoneVisibilityFlag flag = new ZoneVisibilityFlag();
        flag.setZoneVisibility(ZoneVisibility.PRIVATE);

        try {
            FlagLoadTester.testSave(flag);
        } catch (IOException e) {
            Assertions.fail("Could not save", e);
        }
    }

    @Test
    public void testVisibilityLoad() {
        ZoneVisibilityFlag flag = new ZoneVisibilityFlag();
        flag.setZoneVisibility(ZoneVisibility.PRIVATE);

        try {
            ZoneVisibilityFlag loaded = FlagLoadTester.testLoad(flag);

            ZoneVisibility visibility = loaded.getZoneVisibility();
            Assertions.assertEquals(ZoneVisibility.PRIVATE, visibility);
        } catch (IOException e) {
            Assertions.fail("Could not save", e);
        }
    }
}
