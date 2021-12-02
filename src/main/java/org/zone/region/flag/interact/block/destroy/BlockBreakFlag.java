package org.zone.region.flag.interact.block.destroy;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.GroupBoundFlag;
import org.zone.region.group.Group;
import org.zone.region.group.SimpleGroup;

import java.util.Optional;

public class BlockBreakFlag implements GroupBoundFlag.Single<Boolean> {

    private @Nullable Boolean enabled;
    private @NotNull String groupId;

    public static final BlockBreakFlag ELSE = new BlockBreakFlag(SimpleGroup.VISITOR, false);

    public BlockBreakFlag(@NotNull BlockBreakFlag flag) {
        this(flag.getGroupId(), flag.getValue().orElse(null));
    }

    public BlockBreakFlag(@SuppressWarnings("TypeMayBeWeakened") @NotNull Group group, @Nullable Boolean enabled) {
        this(group.getId(), enabled);
    }

    public BlockBreakFlag(@NotNull String groupId, @Nullable Boolean enabled) {
        this.groupId = groupId;
        this.enabled = enabled;
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
    public @NotNull BlockBreakFlagType getType() {
        return FlagTypes.BLOCK_BREAK;
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