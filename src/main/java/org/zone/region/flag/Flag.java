package org.zone.region.flag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.zone.ZonePlugin;
import org.zone.region.Zone;
import org.zone.region.flag.meta.member.MembersFlag;
import org.zone.region.group.Group;
import org.zone.region.group.key.GroupKey;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * A modifier for a Zone. This is designed to hold data or modify the zone in one way or another
 *
 * @since 1.0.0
 */
public interface Flag {

    interface Serializable extends Flag {

        @Override
        @NotNull FlagType.SerializableType<? extends Serializable> getType();

        /**
         * Serializes this flag to the provided ConfigurationNode
         *
         * @param node The node to serialize to
         * @param <T>  The class of this flag
         *
         * @throws IOException If there is an issue saving
         * @since 1.0.0
         */
        default <T extends Flag> void save(@NotNull ConfigurationNode node) throws IOException {
            FlagType.SerializableType<?> type = this.getType();
            ((org.zone.Serializable<T>) type).save(node, (T) this);
        }


    }

    /**
     * If the flag affects players then it should implement this
     *
     * @since 1.0.0
     */
    interface AffectsPlayer extends Flag.GroupKeyed {

        default boolean canBypassEffects(@NotNull Zone zone, @NotNull UUID player) {
            return this.hasPermission(zone, player);
        }

    }

    interface TaggedFlag extends Flag {

        @Override
        @NotNull FlagType.TaggedFlagType<? extends TaggedFlag> getType();
    }

    interface ValueStore extends Flag {

    }

    /**
     * If the flag can be enabled/disabled, then it should implement this to help other plugins to understand your flag
     *
     * @since 1.0.0
     */
    interface Enabled extends Flag.ValueStore {

        /**
         * Gets if the flag has been enabled
         *
         * @return If the flag has been enabled, {@link Optional#empty()} if default value should be used
         * @since 1.0.0
         */
        @NotNull Optional<Boolean> getEnabled();

        /**
         * Gets if the flag has been enabled
         *
         * @return If the flag has been enabled, if the value is null, the default is used instead
         * @since 1.0.0
         */
        boolean isEnabled();

        /**
         * Sets the flag enabled status
         *
         * @param enabled If the flag should be enabled or not. Setting this to null should make it the same as default
         * @since 1.0.0
         */
        void setEnabled(@Nullable Boolean enabled);

    }

    /**
     * If the flag has a group key override, then it should implement this to assist other plugins attempting to understand
     *
     * @since 1.0.0
     */
    interface GroupKeyed extends Flag {
        /**
         * Gets the required GroupKey
         *
         * @return The required GroupKey for overriding
         * @since 1.0.0
         */
        @NotNull GroupKey getRequiredKey();

        /**
         * Checks if a player has permission to override this flag
         *
         * @param zone     the zone this flag is attached to
         * @param playerId The player ID
         *
         * @return If the player has permission
         * @since 1.0.0
         */
        default boolean hasPermission(@NotNull Zone zone, @NotNull UUID playerId) {
            return this.hasPermission(zone.getMembers(), playerId);
        }

        /**
         * Checks if a player has permission to override this flag
         *
         * @param flag     the MemberFlag this flag is part of
         * @param playerId the player ID
         *
         * @return if the player has permission
         * @since 1.0.0
         */
        default boolean hasPermission(@NotNull MembersFlag flag, @NotNull UUID playerId) {
            return this.hasPermission(flag.getGroup(playerId));
        }

        /**
         * Checks if a group has permission to override this flag
         *
         * @param group The group to compare
         *
         * @return If the group has permission
         * @since 1.0.0
         */
        default boolean hasPermission(@NotNull Group group) {
            return group.getAllKeys().contains(this.getRequiredKey());
        }
    }

    /**
     * Gets the attached FlagType to this flag
     *
     * @return The FlagType of this flag
     * @since 1.0.0
     */
    @NotNull FlagType<?> getType();

    /**
     * Finds the zone that this flag belongs to.
     * If another way to find the zone is possible, then please use that as this checks every
     * zone until found
     *
     * @return The attached zone, if {@link Optional#empty()} then no active zone has this flag
     * @since 1.0.0
     */
    default @NotNull Optional<Zone> findAttachedZone() {
        return ZonePlugin
                .getZonesPlugin()
                .getZoneManager()
                .getRegistered()
                .parallelStream()
                .filter(z -> z.containsFlag(Flag.this))
                .findAny();
    }

}
