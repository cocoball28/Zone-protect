package region.flag.meta.request.join;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.zone.region.flag.meta.request.join.JoinRequestFlag;
import tools.CollectionAssert;
import tools.configuration.FlagLoadTester;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class JoinRequestFlagTests {

    private static final Collection<UUID> JOINS = Arrays.asList(UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID());

    @Test
    public void testJoinSaveNull() {
        JoinRequestFlag flag = new JoinRequestFlag(JOINS);

        try {
            if (!FlagLoadTester.testNull(flag)) {
                Assertions.fail("Did not remove children");
            }
        } catch (IOException e) {
            Assertions.fail("Could not save", e);
        }
    }

    @Test
    public void testJoinSave() {
        JoinRequestFlag flag = new JoinRequestFlag(JOINS);

        try {
            FlagLoadTester.testSave(flag);
        } catch (IOException e) {
            Assertions.fail("Could not save", e);
        }
    }

    @Test
    public void testJoinLoad() {
        JoinRequestFlag flag = new JoinRequestFlag(JOINS);

        try {
            JoinRequestFlag loaded = FlagLoadTester.testLoad(flag);

            Collection<UUID> joins = loaded.getJoins();
            CollectionAssert.collectionEquals(JOINS, joins);
        } catch (IOException e) {
            Assertions.fail("Could not save", e);
        }
    }
}
