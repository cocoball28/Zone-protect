package org.zone.commands.structure.region.flags.display.leaving;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.simple.TimeUnitArgument;
import org.zone.commands.system.arguments.simple.number.RangeArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.display.MessageDisplay;
import org.zone.region.flag.entity.player.display.title.TitleMessageDisplayBuilder;
import org.zone.region.flag.entity.player.move.leaving.LeavingFlag;
import org.zone.utils.Messages;
import org.zone.utils.time.TimeUnits;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagLeavingMessageDisplaySetTitleFadeInCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
            new ZoneArgument.ZoneArgumentPropertiesBuilder().setBypassSuggestionPermission(
                    ZonePermissions.OVERRIDE_FLAG_LEAVING_MESSAGE_SET_TITLE_FADE_IN));
    public static final RangeArgument<Integer> FADE_IN = RangeArgument.createArgument("fadeIn", 0
            , Integer.MAX_VALUE);
    public static final TimeUnitArgument UNIT = new TimeUnitArgument("unit",
            Arrays.asList(TimeUnits.TICKS, TimeUnits.SECONDS));

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("flag"),
                             ZONE_ID,
                             new ExactArgument("leaving"),
                             new ExactArgument("message"),
                             new ExactArgument("display"),
                             new ExactArgument("set"),
                             new ExactArgument("title"),
                             FADE_IN,
                             new ExactArgument("unit"),
                             UNIT);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getLeavingDisplaySetTitleFadeInCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_LEAVING_MESSAGE_SET_TITLE_FADE_IN);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        long fadeIn = commandContext.getArgument(this, FADE_IN);
        TemporalUnit unit = commandContext.getArgument(this, UNIT);
        Optional<LeavingFlag> opLeavingFlag = zone.getFlag(FlagTypes.LEAVING);
        if (opLeavingFlag.isEmpty()) {
            return CommandResult.error(Messages.getLeavingFlagNotFound());
        }
        MessageDisplay titleMessageDisplay =
                new TitleMessageDisplayBuilder().setFadeIn(Duration.of(fadeIn, unit)).build();
        opLeavingFlag.get().setDisplayType(titleMessageDisplay);
        try {
            zone.save();
            commandContext.sendMessage(Messages.getFlagMessageDisplaySuccessfullyChangedTo(opLeavingFlag.get().getType(), titleMessageDisplay.getType()));
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
