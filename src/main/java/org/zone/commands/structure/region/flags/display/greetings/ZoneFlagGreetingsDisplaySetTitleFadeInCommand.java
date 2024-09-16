package org.zone.commands.structure.region.flags.display.greetings;

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
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilterBuilder;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilters;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.display.MessageDisplay;
import org.zone.region.flag.entity.player.display.title.TitleMessageDisplayBuilder;
import org.zone.region.flag.entity.player.move.greetings.GreetingsFlag;
import org.zone.utils.Messages;
import org.zone.utils.time.TimeUnits;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagGreetingsDisplaySetTitleFadeInCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
            ZonePermissions.FLAG_GREETINGS_MESSAGE_DISPLAY_SET_TITLE_FADE_IN,
            new ZoneArgumentFilterBuilder().setFilter(ZoneArgumentFilters.INSIDE).build());
    public static final RangeArgument<Integer> FADE_IN = RangeArgument.createArgument("fadeIn", 0
            , Integer.MAX_VALUE);
    public static final TimeUnitArgument UNIT = new TimeUnitArgument("unit",
            Arrays.asList(TimeUnits.TICKS, TimeUnits.SECONDS));

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                new ExactArgument("flag"),
                ZONE_ID,
                new ExactArgument("greetings"),
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
        return Messages.getGreetingsDisplaySetTitleFadeInCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_GREETINGS_MESSAGE_DISPLAY_SET_TITLE_FADE_IN);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        long fadeIn = commandContext.getArgument(this, FADE_IN);
        TemporalUnit timeUnit = commandContext.getArgument(this, UNIT);
        Optional<GreetingsFlag> opGreetingsFlag = zone.getFlag(FlagTypes.GREETINGS);
        if (opGreetingsFlag.isEmpty()) {
            return CommandResult.error(Messages.getGreetingsFlagNotFound());
        }
        MessageDisplay titleMessageDisplay =
                new TitleMessageDisplayBuilder().setFadeIn(Duration.of(fadeIn, timeUnit)).build();
        opGreetingsFlag.get().setDisplayType(titleMessageDisplay);
        try {
            zone.save();
            commandContext.sendMessage(Messages.getFlagMessageDisplaySuccessfullyChangedTo(opGreetingsFlag.get().getType(), titleMessageDisplay.getType()));
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
