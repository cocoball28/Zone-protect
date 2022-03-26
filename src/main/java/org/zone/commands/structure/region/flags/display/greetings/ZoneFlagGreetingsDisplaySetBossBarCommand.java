package org.zone.commands.structure.region.flags.display.greetings;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.simple.EnumArgument;
import org.zone.commands.system.arguments.simple.number.IntegerArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilterBuilder;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilters;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.display.MessageDisplay;
import org.zone.region.flag.entity.player.display.bossbar.BossBarMessageDisplay;
import org.zone.region.flag.entity.player.move.greetings.GreetingsFlag;
import org.zone.region.group.key.GroupKeys;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagGreetingsDisplaySetBossBarCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
            ZonePermissions.OVERRIDE_FLAG_GREETINGS_MESSAGE_DISPLAY_SET_BOSS_BAR,
            new ZoneArgumentFilterBuilder()
                    .setFilter(ZoneArgumentFilters.withGroupKey(GroupKeys.OWNER))
                    .build());

    public static final IntegerArgument PROGRESS = new IntegerArgument("progress");
    public static final EnumArgument<BossBar.Color> COLOR = new EnumArgument<>("color",
            BossBar.Color.class);
    public static final EnumArgument<BossBar.Overlay> OVERLAY = new EnumArgument<>("overlay",
            BossBar.Overlay.class);

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                new ExactArgument("flag"),
                ZONE_ID,
                new ExactArgument("greetings"),
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
        return Messages.getGreetingsDisplaySetBossBarCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_GREETINGS_MESSAGE_DISPLAY_SET_BOSS_BAR);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        float progress = commandContext.getArgument(this, PROGRESS);
        BossBar.Color color = commandContext.getArgument(this, COLOR);
        BossBar.Overlay overlay = commandContext.getArgument(this, OVERLAY);
        Optional<GreetingsFlag> opGreetingsFlag = zone.getFlag(FlagTypes.GREETINGS);
        if (opGreetingsFlag.isEmpty()) {
            return CommandResult.error(Messages.getGreetingsFlagNotFound());
        }
        MessageDisplay bossBarDisplay = new BossBarMessageDisplay(progress, color, overlay);
        opGreetingsFlag.get().setDisplayType(bossBarDisplay);
        zone.setFlag(opGreetingsFlag.get());
        try {
            zone.save();
            commandContext.sendMessage(Messages.getGreetingsDisplaySuccessfullyChangedToBossBar());
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }

}
