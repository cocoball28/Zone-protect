package org.zone.commands.structure.region.flags.interact.destroy;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.zone.Identifiable;
import org.zone.Permissions;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.OptionalArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.interact.block.destroy.BlockBreakFlag;
import org.zone.region.group.key.GroupKeys;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Used for viewing the status of the {@link BlockBreakFlag}
 */
public class ZoneFlagBlockBreakViewCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE = new ZoneArgument("zoneId",
                                                             new ZoneArgument.ZoneArgumentPropertiesBuilder().setLevel(
                                                                     GroupKeys.OWNER));

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                             new ExactArgument("flag"),
                             ZONE,
                             new ExactArgument("interact"),
                             new ExactArgument("block"),
                             new ExactArgument("break"),
                             new OptionalArgument<>(new ExactArgument("view"), (String) null));
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("View the details on door interaction");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.of(Permissions.REGION_ADMIN_INFO.getPermission());
    }

    @Override
    public @NotNull CommandResult run(CommandContext commandContext, String... args) {
        Zone zone = commandContext.getArgument(this, ZONE);
        Optional<BlockBreakFlag> opFlag = zone.getFlag(FlagTypes.BLOCK_BREAK);
        commandContext
                .getCause()
                .sendMessage(Identity.nil(), Component.text("Enabled: " + opFlag.isPresent()).color(NamedTextColor.AQUA));
        //Will change if open issue
        commandContext
                .getCause()
                .sendMessage(Identity.nil(),
                             Component.text("Group: " +
                                                    opFlag.flatMap(flag -> zone
                                                            .getMembers()
                                                            .getGroup(flag.getRequiredKey())
                                                            .map(Identifiable::getName)).orElse("None")));
        return CommandResult.success();
    }
}
