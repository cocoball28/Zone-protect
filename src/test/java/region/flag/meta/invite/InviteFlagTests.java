package region.flag.meta.invite;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.zone.region.flag.meta.invite.InviteFlag;
import tools.CollectionAssert;
import tools.configuration.FlagLoadTester;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class InviteFlagTests {

    private static final Collection<UUID> INVITES = Arrays.asList(UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID());

    @Test
    public void testInviteSaveNull() {
        InviteFlag flag = new InviteFlag(INVITES);

        try {
            if (!FlagLoadTester.testNull(flag)) {
                Assertions.fail("Did not remove children");
            }
        } catch (IOException ioe) {
            Assertions.fail("Could not save", ioe);
        }
    }

    @Test
    public void testInviteSave() {
        InviteFlag flag = new InviteFlag(INVITES);

        try {
            FlagLoadTester.testSave(flag);
        } catch (IOException ioe) {
            Assertions.fail("Could not save", ioe);
        }
    }

    @Test
    public void testInviteLoad() {
        InviteFlag flag = new InviteFlag(INVITES);

        try {
            InviteFlag loaded = FlagLoadTester.testLoad(flag);

            Collection<UUID> invites = loaded.getInvites();
            CollectionAssert.collectionEquals(INVITES, invites);
        } catch (IOException ioe) {
            Assertions.fail("Could not save", ioe);
        }
    }
}
