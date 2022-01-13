package permissions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.zone.permissions.ZonePermissions;

public class Permissions {

    @Test
    public void testNoUpper() {
        for (ZonePermissions permission : ZonePermissions.values()) {
            String permissionNode = permission.getPermission();
            for (int i = 0; i < permissionNode.length(); i++) {
                if (Character.isUpperCase(permissionNode.charAt(i))) {
                    Assertions.fail("Node found upper character in " + permission.name());
                }
            }
        }
    }

    @Test
    public void testNoDupes() {
        for (ZonePermissions permission : ZonePermissions.values()) {
            for (ZonePermissions compare : ZonePermissions.values()) {
                if (permission == compare) {
                    continue;
                }
                if (permission.getPermission().equals(compare.getPermission())) {
                    Assertions.fail("Found duped node in both " +
                                            permission.name() +
                                            " and " +
                                            compare.name());
                }
            }
        }
    }
}
