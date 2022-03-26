package org.zone.commands.structure.region.flags.display.greetings;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.OptionalArgument;
import org.zone.commands.system.arguments.simple.number.IntegerArgument;
import org.zone.commands.system.arguments.sponge.ComponentRemainingArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilterBuilder;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilters;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.display.MessageDisplay;
import org.zone.region.flag.entity.player.display.title.TitleMessageDisplay;
import org.zone.region.flag.entity.player.move.greetings.GreetingsFlag;
import org.zone.region.group.key.GroupKeys;
import org.zone.utils.Messages;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagGreetingsDisplaySetTitleCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",ZonePermissions.OVERRIDE_FLAG_GREETINGS_MESSAGE_DISPLAY_SET_TITLE,            new ZoneArgumentFilterBuilder()
            .setFilter(ZoneArgumentFilters.withGroupKey(GroupKeys.OWNER))
            .build());

    public static final OptionalArgument<Component> SUB_TITLE = new OptionalArgument<>(new ComponentRemainingArgument(
            "subTitle"), (Component) null);
    public static final OptionalArgument<Integer> FADE_IN = new OptionalArgument<>(new IntegerArgument(
            "fadeIn"), (Integer) null);
    public static final OptionalArgument<Integer> STAY = new OptionalArgument<>(new IntegerArgument(
            "stay"), (Integer) null);
    public static final OptionalArgument<Integer> FADE_OUT = new OptionalArgument<>(new IntegerArgument(
            "fadeOut"), (Integer) null);

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
                SUB_TITLE,
                FADE_IN,
                STAY,
                FADE_OUT);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getGreetingsDisplaySetTitleCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_GREETINGS_MESSAGE_DISPLAY_SET_TITLE);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        Component subTitle = commandContext.getArgument(this, SUB_TITLE);
        long fadeIn = commandContext.getArgument(this, FADE_IN);
        long stay = commandContext.getArgument(this, STAY);
        long fadeOut = commandContext.getArgument(this, FADE_OUT);
        Optional<GreetingsFlag> opGreetingsFlag = zone.getFlag(FlagTypes.GREETINGS);
        if (opGreetingsFlag.isEmpty()) {
            return CommandResult.error(Messages.getGreetingsFlagNotFound());
        }
        MessageDisplay titleMessageDisplay = new TitleMessageDisplay(subTitle,
                Title.Times.of(Duration.ofSeconds(fadeIn),
                        Duration.ofSeconds(stay),
                        Duration.ofSeconds(fadeOut)));
        opGreetingsFlag.get().setDisplayType(titleMessageDisplay);
        zone.setFlag(opGreetingsFlag.get());
        try {
            zone.save();
            commandContext.sendMessage(Messages.getGreetingsDisplaySuccessfullyChangedToTitle());
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
