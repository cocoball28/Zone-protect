package org.zone.commands.structure.region.flags.display.greetings;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.sponge.ComponentRemainingArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.player.display.MessageDisplay;
import org.zone.region.flag.entity.player.display.title.TitleMessageDisplayBuilder;
import org.zone.region.flag.entity.player.move.greetings.GreetingsFlag;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagGreetingsDisplaySetTitleSubtitleCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
            new ZoneArgument.ZoneArgumentPropertiesBuilder().setBypassSuggestionPermission(
                    ZonePermissions.OVERRIDE_FLAG_GREETINGS_MESSAGE_DISPLAY_SET_TITLE_SUBTITLE));
    public static final ComponentRemainingArgument SUB_TITLE = new ComponentRemainingArgument(
            "subTitle");

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
                SUB_TITLE);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getGreetingsDisplaySetTitleSubtitleCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_GREETINGS_MESSAGE_DISPLAY_SET_TITLE_SUBTITLE);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        Component subtitle = commandContext.getArgument(this, SUB_TITLE);
        Optional<GreetingsFlag> opGreetingsFlag = zone.getFlag(FlagTypes.GREETINGS);
        if (opGreetingsFlag.isEmpty()) {
            return CommandResult.error(Messages.getGreetingsFlagNotFound());
        }
        MessageDisplay titleMessageDisplay =
                new TitleMessageDisplayBuilder().setSubTitle(subtitle).build();
        opGreetingsFlag.get().setDisplayType(titleMessageDisplay);
        try {
            zone.save();
            commandContext.sendMessage(Messages.getFlagMessageDisplayUpdated(opGreetingsFlag.get().getType(), titleMessageDisplay.getType()));
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
