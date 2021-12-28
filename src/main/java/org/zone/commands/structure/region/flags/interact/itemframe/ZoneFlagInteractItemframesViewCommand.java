package org.zone.commands.structure.region.flags.interact.itemframe;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.zone.Identifiable;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.operation.OptionalArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.interact.itemframe.InteractItemframesFlag;
import org.zone.region.group.key.GroupKeys;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Used to view the details of {@link InteractItemframesFlag}
 */
public class ZoneFlagInteractItemframesViewCommand implements ArgumentCommand {

    public static final ExactArgument REGION = new ExactArgument("region");
    public static final ExactArgument FLAG = new ExactArgument("flag");
    public static final ZoneArgument ZONE_VALUE = new ZoneArgument("zone_value");
    public static final ExactArgument INTERACT = new ExactArgument("interact");
    public static final ExactArgument ITEMFRAMES = new ExactArgument("itemframes");
    public static final OptionalArgument VIEW = new OptionalArgument<>(new ExactArgument("view"), (String) null);

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(REGION, FLAG, ZONE_VALUE, INTERACT, ITEMFRAMES, VIEW);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("View the details of Interact Itemframe");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(@NotNull CommandContext commandContext, @NotNull String[] args) {
        Zone zone = commandContext.getArgument(this, ZONE_VALUE);
        @NotNull InteractItemframesFlag interactItemframesFlag = zone
                .getFlag(FlagTypes.INTERACT_ITEMFRAMES_FLAG_TYPE)
                .orElse(new InteractItemframesFlag(InteractItemframesFlag.ELSE));
        commandContext
                .getCause()
                .sendMessage(Identity.nil(), Component.text("Enabled: " + interactItemframesFlag.isEnabled()));
        commandContext
                .getCause()
                .sendMessage(Identity.nil(),
                             Component.text("Group: " +
                                     zone
                                             .getMembers()
                                             .getGroup(GroupKeys.INTERACT_ITEMFRAME)
                                             .map(Identifiable::getName)
                                             .orElse("None")));
        return CommandResult.success();
    }
}