package org.zone.commands.structure.region.flags.entry.monster;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.OptionalArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.entity.monster.move.PreventMonsterFlag;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagMonsterEntryViewCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_VALUE = new ZoneArgument("zoneId");
    public static final OptionalArgument<String> VIEW = new OptionalArgument<>(new ExactArgument(
            "view"), (String) null);

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("flag"),
                             ZONE_VALUE,
                             new ExactArgument("monster"),
                             new ExactArgument("entry"),
                             VIEW);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Displays info about the Monster Prevention flag");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(@NotNull CommandContext commandContext,
                                      @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_VALUE);
        PreventMonsterFlag preventMonsterFlag = zone
                .getFlag(FlagTypes.PREVENT_MONSTER)
                .orElse(FlagTypes.PREVENT_MONSTER.createCopyOfDefault());
        commandContext
                .getCause()
                .sendMessage(Identity.nil(),
                             Component.text("Enabled: " + zone.containsFlag(FlagTypes.PREVENT_MONSTER))
                                     .color(NamedTextColor.AQUA));
        return CommandResult.success();
    }
}
