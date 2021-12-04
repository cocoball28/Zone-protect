package org.zone.region.flag;

import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.zone.ZonePlugin;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class DefaultFlagFile {

    public static final File FILE = new File("config/zones/DefaultZone.conf");
    private final HoconConfigurationLoader loader;
    private final ConfigurationNode node;

    public DefaultFlagFile() {
        this.loader = HoconConfigurationLoader
                .builder()
                .file(FILE)
                .build();

        ConfigurationNode node1;
        try {
            node1 = this.loader.load();
        } catch (ConfigurateException e) {
            node1 = this.loader.createNode();
            this.updateFile();
        }
        this.node = node1;
    }

    private void updateFile() {
        try {
            if (!FILE.exists()) {
                FILE.getParentFile().mkdirs();

                FILE.createNewFile();
            }
            for (FlagType<? extends Flag> type : ZonePlugin.getZonesPlugin().getFlagManager().getRegistered()) {
                Optional<? extends Flag> opFlag = this.loadDefault(type);
                if (opFlag.isEmpty()) {
                    this.removeDefault(type);
                    continue;
                }
                this.setDefault(opFlag.get());
            }
            this.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <F extends Flag, T extends FlagType<F>> Optional<F> loadDefault(T type) {
        try {
            return Optional.of(type.load(this.node.node("flags", type.getPlugin().metadata().id(), type.getKey())));
        } catch (IOException e) {
            return type.createCopyOfDefaultFlag();
        }
    }

    public <F extends Flag, T extends FlagType<F>> void setDefault(F flag) throws IOException {
        T type = (T) flag.getType();
        type.save(this.node.node("flags", type.getPlugin().metadata().id(),
                type.getKey()), flag);
    }

    public void removeDefault(FlagType<? extends Flag> type) throws IOException {
        type.save(this.node.node("flags"), null);
    }

    public void save() throws ConfigurateException {
        this.loader.save(this.node);
    }
}
