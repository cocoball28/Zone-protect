package permissions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.zone.permissions.ZonePermissions;

public class OverridePermissions {

    @Test
    public void testAllOverridesAreDefaultFalse() {
        for (ZonePermissions permission : ZonePermissions.values()) {
            if (!permission.name().startsWith("OVERRIDE_")) {
                continue;
            }
            if (permission.isDefaultAllowed()) {
                Assertions.fail("The override permission of " +
                                        permission.name() +
                                        " should not " +
                                        "have a default value of true");
            }
        }
    }

    @Test
    public void testAllCommandsHaveOverrides() {
        for (ZonePermissions permission : ZonePermissions.values()) {
            String[] node = permission.getPermissionNode();
            if (node.length <= 2) {
                continue;
            }
            if (!node[0].equals("zone")) {
                continue;
            }
            if (!node[1].equals("region")) {
                continue;
            }
            ZonePermissions overridePermission = ZonePermissions.valueOf("OVERRIDE_" +
                                                                                 permission.name());
            if (overridePermission == null) {
                Assertions.fail("No override permission found for " + permission.name());
            }

            String[] overrideNode = overridePermission.getPermissionNode();
            Assertions.assertEquals(overrideNode.length, node.length + 1);
            for (int i = 2; i < overrideNode.length; i++) {
                Assertions.assertEquals(overrideNode[i],
                                        node[i],
                                        "permission of " +
                                                permission.name() +
                                                " does not match " +
                                                "the node of " +
                                                overridePermission.name() +
                                                " with the exception of pos2 being override. " +
                                                "difference found at pos " +
                                                (i + 1));
            }
        }
    }
}
