package org.zone.commands.structure.region.flags.display.leaving;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.simple.EnumArgument;
import org.zone.commands.system.arguments.simple.number.RangeArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilterBuilder;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilters;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.display.MessageDisplay;
import org.zone.region.flag.entity.player.display.bossbar.BossBarMessageDisplayBuilder;
import org.zone.region.flag.entity.player.move.leaving.LeavingFlag;
import org.zone.region.group.key.GroupKeys;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagLeavingMessageDisplaySetBossBarCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
                        ZonePermissions.OVERRIDE_FLAG_LEAVING_MESSAGE_SET_BOSS_BAR,
                        new ZoneArgumentFilterBuilder()
                                        .setFilter(ZoneArgumentFilters.withGroupKey(GroupKeys.OWNER))
                                        .setPermission(ZonePermissions.FLAG_LEAVING_MESSAGE_SET_BOSS_BAR)
                                        .build());
    public static final RangeArgument<Integer> PROGRESS = RangeArgument.createArgument("progress"
            , 0, 100);
    public static final EnumArgument<BossBar.Color> COLOR = new EnumArgument<>(
            "color", BossBar.Color.class);
    public static final EnumArgument<BossBar.Overlay> OVERLAY = new EnumArgument<>("overlay",
            BossBar.Overlay.class);

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("flag"),
                             ZONE_ID,
                             new ExactArgument("leaving"),
                             new ExactArgument("message"),
                             new ExactArgument("display"),
                             new ExactArgument("set"),
                             new ExactArgument("boss"),
                             new ExactArgument("bar"),
                             PROGRESS,
                             COLOR,
                             OVERLAY);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getLeavingDisplaySetBossBarCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_LEAVING_MESSAGE_SET_BOSS_BAR);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        float progress = commandContext.getArgument(this, PROGRESS);
        BossBar.Color color = commandContext.getArgument(this, COLOR);
        BossBar.Overlay overlay = commandContext.getArgument(this, OVERLAY);
        Optional<LeavingFlag> opLeavingFlag = zone.getFlag(FlagTypes.LEAVING);
        if (opLeavingFlag.isEmpty()) {
            return CommandResult.error(Messages.getLeavingFlagNotFound());
        }
        MessageDisplay bossBarDisplay =
                new BossBarMessageDisplayBuilder().setProgress(progress).setColor(color).setOverlay(overlay).build();
        opLeavingFlag.get().setDisplayType(bossBarDisplay);
        try {
            zone.save();
            commandContext.sendMessage(Messages.getFlagMessageDisplaySuccessfullyChangedTo(opLeavingFlag.get().getType(), bossBarDisplay.getType()));
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
