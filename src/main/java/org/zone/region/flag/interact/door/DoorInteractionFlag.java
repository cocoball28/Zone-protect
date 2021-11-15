package org.zone.region.flag.interact.door;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.GroupBoundFlag;
import org.zone.region.group.Group;
import org.zone.region.group.SimpleGroup;

import java.util.Optional;

public class DoorInteractionFlag implements GroupBoundFlag.Single<Boolean> {

    private @Nullable Boolean enabled;
    private @NotNull String groupId;

    public static final DoorInteractionFlag ELSE = new DoorInteractionFlag(SimpleGroup.VISITOR, false);

    public DoorInteractionFlag(DoorInteractionFlag flag) {
        this(flag.groupId, flag.enabled!=null && flag.enabled);
    }

    public DoorInteractionFlag(@SuppressWarnings("TypeMayBeWeakened") @NotNull Group group, boolean enabled) {
        this(group.getId(), enabled);
    }

    public DoorInteractionFlag(@NotNull String groupId, boolean enabled) {
        this.enabled = enabled;
        this.groupId = groupId;
    }

    @Override
    public @NotNull Optional<Boolean> getValue() {
        return Optional.ofNullable(this.enabled);
    }

    @Override
    public void setValue(@Nullable Boolean flag) {
        this.enabled = flag;
    }

    @Override
    public @NotNull DoorInteractionFlagType getType() {
        return FlagTypes.DOOR_INTERACTION;
    }

    @Override
    public @NotNull String getGroupId() {
        return this.groupId;
    }

    @Override
    public void setGroupId(@NotNull String groupId) {
        this.groupId = groupId;
    }
}
