package org.zone.commands.structure.zone.flags.interact.destroy;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.zone.Identifiable;
import org.zone.Permissions;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.NotEnoughArgumentsException;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.OptionalArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.interact.block.destroy.BlockBreakFlag;
import org.zone.region.flag.interact.door.DoorInteractionFlag;
import org.zone.region.group.SimpleGroup;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagBlockBreakViewCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE = new ZoneArgument("zoneId", new ZoneArgument
            .ZoneArgumentPropertiesBuilder()
            .setLevel(SimpleGroup.OWNER)
    );

    @Override
    public List<CommandArgument<?>> getArguments() {
        return Arrays.asList(
                new ExactArgument("zone"),
                new ExactArgument("flag"),
                ZONE,
                new ExactArgument("interact"),
                new ExactArgument("block"),
                new ExactArgument("break"),
                new OptionalArgument<>(new ExactArgument("view"), (String) null));
    }

    @Override
    public Component getDescription() {
        return Component.text("View the details on door interaction");
    }

    @Override
    public Optional<String> getPermissionNode() {
        return Optional.of(Permissions.REGION_ADMIN_INFO.getPermission());
    }

    @Override
    public CommandResult run(CommandContext commandContext, String... args) throws NotEnoughArgumentsException {
        Zone zone = commandContext.getArgument(this, ZONE);
        @NotNull BlockBreakFlag flag = zone
                .getFlag(FlagTypes.BLOCK_BREAK)
                .orElse(new BlockBreakFlag(BlockBreakFlag.ELSE));
        commandContext.getCause().sendMessage(Identity.nil(), Component
                .text("Enabled: " + flag.getValue().orElse(false)));
        commandContext
                .getCause()
                .sendMessage(Identity.nil(), Component
                        .text("Group: " + flag
                                .getGroup(zone.getMembers())
                                .map(Identifiable::getName)
                                .orElse(flag.getGroupId())));
        return CommandResult.success();
    }
}
