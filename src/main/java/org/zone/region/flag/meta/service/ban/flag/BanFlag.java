package org.zone.region.flag.meta.service.ban.flag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.Flag;
import org.zone.region.flag.FlagType;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.meta.service.ban.BanInfo;
import org.zone.region.flag.meta.service.ban.PermanentBanInfo;
import org.zone.region.flag.meta.service.ban.TemporaryBanInfo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Flag for banning players from a zone
 */
public class BanFlag implements Flag.Serializable {

    /**
     * Collection of ban info
     */
    private final Collection<BanInfo> banInfo = new HashSet<>();

    /**
     * Constructor of BanFlag class
     *
     * @param banInfo the ban information in the form of a collection
     */
    public BanFlag(Collection<? extends BanInfo> banInfo) {
        this.banInfo.addAll(banInfo);
    }

    /**
     * Second constructor of BanFlag class that can accept a none param
     *
     * @param banInfo the ban information in the form of varag arrays
     */
    public BanFlag(BanInfo... banInfo) {
        this(Arrays.asList(banInfo));
    }

    /**
     * Gets the collection of ban information
     *
     * @return the ban info collection
     */
    public @NotNull Collection<BanInfo> getBanInfo() {
        return this.banInfo;
    }

    /**
     * Temporarily bans a player from a zone
     *
     * @param uuid The uuid of the player
     *
     * @param localDateTime The local date and time of when the player was banned (this can be null)
     */
    public void banPlayer(@NotNull UUID uuid, @Nullable LocalDateTime localDateTime) {
        BanInfo banInfo;
        if (localDateTime == null) {
            banInfo = new PermanentBanInfo(uuid);
        } else {
            banInfo = new TemporaryBanInfo(uuid, localDateTime);
        }
        this.banInfo.add(banInfo);
    }

    /**
     * Permanently bans a player from a zone
     *
     * @param uuid The uuid of the player
     */
    public void banPlayer(@NotNull UUID uuid) {
        this.banPlayer(uuid, null);
    }

    /**
     * Unbans a player from a zone
     *
     * @param uuid the of the player
     */
    public void unbanPlayer(@NotNull UUID uuid) {
        Optional<BanInfo> banInfo = this.banInfo
                .stream()
                .filter(banInfo1 -> banInfo1.getId().equals(uuid))
                .findAny();
        if (banInfo.isEmpty()) {
            return;
        }
        this.banInfo.remove(banInfo.get());
    }

    /**
     * Check if the player is banned from the zone
     *
     * @param uuid the uuid of the player
     *
     * @return a boolean checking whether the player is in the list of banned players
     */
    public boolean isBanned(@NotNull UUID uuid) {
        return this.banInfo.stream().anyMatch(banInfo1 -> banInfo1.getId().equals(uuid));
    }

    /**
     * Gets the type class of this flag
     *
     * @return The type from {@link FlagTypes} class. Here, {@link FlagTypes#BAN}
     */
    @Override
    public @NotNull FlagType.SerializableType<? extends Serializable> getType() {
        return FlagTypes.BAN;
    }

}
