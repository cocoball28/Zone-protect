package org.zone.commands.structure.region.flags.interact.itemframe;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.structure.misc.Messages;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.ZoneGroupArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.interact.itemframe.ItemFrameInteractFlag;
import org.zone.region.group.Group;
import org.zone.region.group.key.GroupKeys;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Used ot modify the group that the groupkey belongs to {@link ItemFrameInteractFlag}
 */
public class ZoneFlagInteractItemframesGroupCommand implements ArgumentCommand {

    public static final ExactArgument REGION = new ExactArgument("region");
    public static final ExactArgument FLAG = new ExactArgument("flag");
    public static final ZoneArgument ZONE_VALUE = new ZoneArgument("zone_value", new ZoneArgument.ZoneArgumentPropertiesBuilder().setLevel(GroupKeys.INTERACT_ITEMFRAME));
    public static final ExactArgument INTERACT = new ExactArgument("interact");
    public static final ExactArgument ITEMFRAMES = new ExactArgument("itemframes");
    public static final ZoneGroupArgument GROUP = new ZoneGroupArgument("groupID", ZONE_VALUE);
    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(REGION, FLAG, ZONE_VALUE, INTERACT, ITEMFRAMES, new ExactArgument("group"), GROUP);
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
    public @NotNull CommandResult run(@NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_VALUE);
        @NotNull ItemFrameInteractFlag interactItemframesFlag = zone
                .getFlag(FlagTypes.ITEM_FRAME_INTERACT)
                .orElseGet(() -> new ItemFrameInteractFlag(ItemFrameInteractFlag.ELSE));
        Group newGroup = commandContext.getArgument(this, GROUP);
        zone.getMembers().addKey(newGroup, interactItemframesFlag.getRequiredKey());
        commandContext
                .getCause()
                .sendMessage(Identity.nil(), Messages.getUniversalOnlyZoneFlagInteractDoorCommandsFlagSaved());
        zone.setFlag(interactItemframesFlag);
        try {
            zone.save();
            commandContext
                    .getCause()
                    .sendMessage(Identity.nil(), Messages.getUniversalOnlyZoneFlagInteractItemframesCommandFlagSaved());
        }catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Messages.getUniversalZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}