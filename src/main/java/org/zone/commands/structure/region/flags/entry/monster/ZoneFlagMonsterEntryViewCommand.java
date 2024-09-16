package org.zone.commands.structure.region.flags.entry.monster;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.OptionalArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilterBuilder;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilters;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.group.key.GroupKeys;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagMonsterEntryViewCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_VALUE = new ZoneArgument("zoneId",
            ZonePermissions.OVERRIDE_FLAG_ENTRY_MONSTER_VIEW,
            new ZoneArgumentFilterBuilder()
                    .setFilter(ZoneArgumentFilters.withGroupKey(GroupKeys.OWNER))
                    .build());
    public static final OptionalArgument<Optional<String>> VIEW = OptionalArgument.createArgument(
            new ExactArgument("view"));

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                new ExactArgument("flag"),
                ZONE_VALUE,
                new ExactArgument("entry"),
                new ExactArgument("monster"),
                VIEW);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getMonsterEntryViewCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_ENTRY_MONSTER_VIEW);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_VALUE);
        commandContext
                .getCause()
                .sendMessage(Identity.nil(),
                        Messages.getEnabledInfo(zone.containsFlag(FlagTypes.PREVENT_MONSTER)));
        return CommandResult.success();
    }
}
