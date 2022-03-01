package tools.configuration;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.zone.region.flag.Flag;

import java.io.IOException;

public final class FlagLoadTester {

    private FlagLoadTester() {
        throw new RuntimeException("Should not init");
    }

    public static <T extends Flag.Serializable> boolean testNull(@NotNull T type) throws
            IOException {
        ConfigurationNode root = new MemoryConfigurationNode("root", null);
        type.save(root);
        type.getType().save(root, null);
        return root.childrenList().isEmpty();
    }

    public static <T extends Flag.Serializable> void testSave(@NotNull T toSave) throws
            IOException {
        ConfigurationNode root = new MemoryConfigurationNode("root", null);
        toSave.save(root);
    }

    public static <T extends Flag.Serializable> @NotNull T testLoad(@NotNull T toSave) throws
            IOException {
        ConfigurationNode root = new MemoryConfigurationNode("root", null);
        toSave.save(root);

        return (T) toSave.getType().load(root);
    }
}
