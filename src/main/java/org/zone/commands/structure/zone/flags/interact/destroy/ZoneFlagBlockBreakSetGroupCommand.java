package org.zone.commands.structure.zone.flags.interact.destroy;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.NotEnoughArgumentsException;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.ZoneGroupArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.interact.block.destroy.BlockBreakFlag;
import org.zone.region.group.DefaultGroups;
import org.zone.region.group.Group;
import org.zone.region.group.SimpleGroup;
import org.zone.region.group.key.GroupKeys;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneFlagBlockBreakSetGroupCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE = new ZoneArgument("zoneId",
            new ZoneArgument
                    .ZoneArgumentPropertiesBuilder()
                    .setLevel(GroupKeys.OWNER));
    public static final ZoneGroupArgument GROUP = new ZoneGroupArgument("groupid", ZONE);

    @Override
    public List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("zone"),
                new ExactArgument("flag"),
                ZONE,
                new ExactArgument("interact"),
                new ExactArgument("block"),
                new ExactArgument("break"),
                new ExactArgument("group"),
                GROUP);
    }

    @Override
    public Component getDescription() {
        return Component.text("Sets the group ");
    }

    @Override
    public Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public CommandResult run(CommandContext commandContext, String... args) throws NotEnoughArgumentsException {
        Zone zone = commandContext.getArgument(this, ZONE);
        @NotNull BlockBreakFlag flag = zone
                .getFlag(FlagTypes.BLOCK_BREAK)
                .orElseGet(() -> new BlockBreakFlag(BlockBreakFlag.ELSE));
        Group newGroup = commandContext.getArgument(this, GROUP);
        zone.getMembers().addKey(newGroup, flag.getRequiredKey());
        zone.addFlag(flag);
        try {
            zone.save();
            commandContext.getCause().sendMessage(Identity.nil(), Component.text("Updated Block Break Interaction"));
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return CommandResult.error(Component.text("Could not save: " + e.getMessage()));
        }
        return CommandResult.success();
    }
}
