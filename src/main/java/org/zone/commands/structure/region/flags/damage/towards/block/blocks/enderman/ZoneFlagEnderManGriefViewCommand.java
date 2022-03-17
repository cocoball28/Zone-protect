package org.zone.commands.structure.region.flags.damage.towards.block.blocks.enderman;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.OptionalArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagEnderManGriefViewCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
            new ZoneArgument.ZoneArgumentPropertiesBuilder()
                    .setBypassSuggestionPermission(ZonePermissions.OVERRIDE_FLAG_ENDERMAN_GRIEF_VIEW));
    public static final OptionalArgument<String> VIEW = new OptionalArgument<>(new ExactArgument("view"),
            (String) null);

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                new ExactArgument("flag"),
                ZONE_ID,
                new ExactArgument("grief"),
                new ExactArgument("enderman"),
                VIEW);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getEnderManGriefViewCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.FLAG_ENDERMAN_GRIEF_VIEW);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        commandContext
                .getCause()
                .sendMessage(Identity.nil(),
                        Messages.getEnabledInfo(zone.containsFlag(FlagTypes.ENDER_MAN_GRIEF)));
        return CommandResult.success();
    }
}
