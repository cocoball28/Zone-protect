package tools.configuration;

import org.spongepowered.configurate.ConfigurationNode;
import org.zone.region.flag.Flag;

import java.io.IOException;

public class FlagLoadTester {

    public static <T extends Flag> boolean testNull(T type) throws IOException {
        ConfigurationNode root = new MemoryConfigurationNode("root", null);
        type.save(root);
        type.getType().save(root, null);
        return root.childrenList().isEmpty();
    }

    public static <T extends Flag> void testSave(T toSave) throws IOException {
        ConfigurationNode root = new MemoryConfigurationNode("root", null);
        toSave.save(root);
    }

    public static <T extends Flag> T testLoad(T toSave) throws IOException {
        ConfigurationNode root = new MemoryConfigurationNode("root", null);
        toSave.save(root);

        return (T) toSave.getType().load(root);
    }
}
