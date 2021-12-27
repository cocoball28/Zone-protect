package org.zone.commands.structure.region.flags.interact.itemframe;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.ZoneGroupArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.interact.itemframe.InteractItemframesFlag;
import org.zone.region.group.Group;
import org.zone.region.group.key.GroupKeys;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Used ot modify the group that the groupkey belongs to {@link InteractItemframesFlag}
 */
public class ZoneFlagInteractItemframesGroupCommand implements ArgumentCommand {

    public static final ExactArgument REGION = new ExactArgument("region");
    public static final ExactArgument FLAG = new ExactArgument("flag");
    public static final ZoneArgument ZONE_VALUE = new ZoneArgument("zone_value", new ZoneArgument.ZoneArgumentPropertiesBuilder().setLevel(GroupKeys.INTERACT_ITEMFRAME));
    public static final ExactArgument INTERACT_ITEMFRAMES = new ExactArgument("interactitemframes");
    public static final ZoneGroupArgument GROUP = new ZoneGroupArgument("groupID", ZONE_VALUE);
    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(REGION, FLAG, ZONE_VALUE, INTERACT_ITEMFRAMES, new ExactArgument("group"), GROUP);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Sets the minimum group that can interact with itemframes");
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
                .orElseGet(() -> new InteractItemframesFlag(InteractItemframesFlag.ELSE));
        Group newGroup = commandContext.getArgument(this, GROUP);
        zone.getMembers().addKey(newGroup, interactItemframesFlag.getRequiredKey());
        commandContext
                .getCause()
                .sendMessage(Identity.nil(), Component.text("Updated Interact Itemframes"));
        zone.setFlag(interactItemframesFlag);
        try {
            zone.save();
            commandContext
                    .getCause()
                    .sendMessage(Identity.nil(), Component.text("Updated Interact Itemframes"));
        }catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Component.text("Could not save because " + ce.getMessage()));
        }
        return CommandResult.success();
    }
}