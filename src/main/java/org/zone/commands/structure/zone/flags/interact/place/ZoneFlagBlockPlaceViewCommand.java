package org.zone.commands.structure.zone.flags.interact.place;

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
import org.zone.region.flag.interact.block.place.BlockPlaceFlag;
import org.zone.region.group.key.GroupKeys;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagBlockPlaceViewCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE = new ZoneArgument("zoneId", new ZoneArgument.ZoneArgumentPropertiesBuilder().setLevel(GroupKeys.OWNER));

    @Override
    public List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("zone"), new ExactArgument("flag"), ZONE, new ExactArgument("interact"), new ExactArgument("block"), new ExactArgument("place"), new OptionalArgument<>(new ExactArgument("view"), (String) null));
    }

    @Override
    public Component getDescription() {
        return Component.text("View the details on block placement");
    }

    @Override
    public Optional<String> getPermissionNode() {
        return Optional.of(Permissions.REGION_ADMIN_INFO.getPermission());
    }

    @Override
    public CommandResult run(CommandContext commandContext, String... args) throws NotEnoughArgumentsException {
        Zone zone = commandContext.getArgument(this, ZONE);
        @NotNull BlockPlaceFlag flag = zone
                .getFlag(FlagTypes.BLOCK_PLACE)
                .orElse(new BlockPlaceFlag(BlockPlaceFlag.DEFAULT));
        commandContext.getCause().sendMessage(Identity.nil(), Component.text("Enabled: " + flag.isEnabled()));
        commandContext
                .getCause()
                .sendMessage(Identity.nil(), Component.text("Group: " +
                        zone.getMembers().getGroup(flag.getRequiredKey()).map(Identifiable::getName).orElse("None")));
        return CommandResult.success();
    }
}
