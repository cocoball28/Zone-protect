package org.zone.region.flag.meta.service.ban.flag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.plugin.PluginContainer;
import org.zone.ZonePlugin;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.meta.service.ban.BanInfo;
import org.zone.region.flag.meta.service.ban.PermanentBanInfo;
import org.zone.region.flag.meta.service.ban.TemporaryBanInfo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class BanFlagType implements FlagType.SerializableType<BanFlag> {

    @Override
    public @NotNull String getName() {
        return "Ban";
    }

    @Override
    public @NotNull PluginContainer getPlugin() {
        return ZonePlugin.getZonesPlugin().getPluginContainer();
    }

    @Override
    public @NotNull String getKey() {
        return "ban";
    }

    @Override
    public @NotNull BanFlag load(@NotNull ConfigurationNode node) throws IOException {
        List<? extends ConfigurationNode> bannedPlayersNode =
                node.node("BannedPlayers").childrenList();
        Collection<BanInfo> banInfo = bannedPlayersNode
                .stream()
                .map(node1 -> {
                    String bannedPlayerUUID = node1.node("ID").getString();
                    String releaseTime = node1.node("ReleaseTime").getString();
                    if (bannedPlayerUUID == null) {
                        ZonePlugin.getZonesPlugin().getLogger().error("Unknown UUID of banned player");
                        //noinspection ReturnOfNull
                        return null;
                    }
                    if (releaseTime == null) {
                        return new PermanentBanInfo(UUID.fromString(bannedPlayerUUID));
                    } else {
                        return new TemporaryBanInfo(UUID.fromString(bannedPlayerUUID),
                                LocalDateTime.parse(releaseTime, DateTimeFormatter.ISO_DATE_TIME));
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return new BanFlag(banInfo);
    }

    @Override
    public void save(
            @NotNull ConfigurationNode node, @Nullable BanFlag save) throws IOException {
        if (save == null) {
            node.set(null);
            return;
        }
        ConfigurationNode bannedPlayersNode = node.node("BannedPlayers");
        for (BanInfo info : save.getBanInfo()) {
            ConfigurationNode banNode = bannedPlayersNode.appendListNode();
            banNode.node("ID").set(info.getId().toString());
            if (info instanceof TemporaryBanInfo tempBanInfo) {
                String formattedReleaseTime =
                        tempBanInfo.getReleaseTime().format(DateTimeFormatter.ISO_DATE_TIME);
                banNode.node("ReleaseTime").set(formattedReleaseTime);
            }
        }
    }

    @Override
    public @NotNull Optional<BanFlag> createCopyOfDefaultFlag() {
        return Optional.empty();
    }
}
