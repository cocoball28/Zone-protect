package org.zone.region.flag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.zone.region.Zone;
import org.zone.region.flag.meta.member.MembersFlag;
import org.zone.region.group.Group;
import org.zone.region.group.key.GroupKey;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public interface Flag {

    interface Enabled extends Flag {

        void setEnabled(@Nullable Boolean enabled);

        Optional<Boolean> getEnabled();

        boolean isEnabled();

    }

    interface GroupKeyed extends Flag {
        @NotNull GroupKey getRequiredKey();

        default boolean hasPermission(@NotNull Zone zone, @NotNull UUID playerId) {
            return this.hasPermission(zone.getMembers(), playerId);
        }

        default boolean hasPermission(@NotNull MembersFlag flag, @NotNull UUID playerId) {
            return this.hasPermission(flag.getGroup(playerId));
        }

        default boolean hasPermission(@NotNull Group group) {
            return group.getAllKeys().contains(this.getRequiredKey());
        }
    }

    @NotNull FlagType<?> getType();

    default <T extends Flag> void save(ConfigurationNode node) throws IOException {
        ((FlagType<T>) this.getType()).save(node, (T) this);
    }

}
