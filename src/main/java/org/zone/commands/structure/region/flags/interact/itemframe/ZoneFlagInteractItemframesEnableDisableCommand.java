package org.zone.commands.structure.region.flags.interact.itemframe;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.simple.BooleanArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.FlagTypes;
import org.zone.region.flag.interact.itemframe.InteractItemframesFlag;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
`* Used to enable/disable the flag {@link InteractItemframesFlag}
 */

public class ZoneFlagInteractItemframesEnableDisableCommand implements ArgumentCommand {

    public static final ExactArgument REGION = new ExactArgument("region");
    public static final ExactArgument FLAG = new ExactArgument("flag");
    public static final ZoneArgument ZONE_VALUE = new ZoneArgument("zone_value", new ZoneArgument.ZoneArgumentPropertiesBuilder());
    public static final ExactArgument INTERACT_ITEMFRAMES = new ExactArgument("interactitemframes");
    public static final BooleanArgument ENABLEDISABLE = new BooleanArgument("enableValue", "enable", "disable");

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(REGION, FLAG, ZONE_VALUE, INTERACT_ITEMFRAMES, ENABLEDISABLE);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Command to enable/disable Itemframe interaction");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.empty();
    }

    @Override
    public @NotNull CommandResult run(@NotNull CommandContext commandContext, @NotNull String[] args) {
        boolean enable = commandContext.getArgument(this, ENABLEDISABLE);
        Zone zone = commandContext.getArgument(this, ZONE_VALUE);
        InteractItemframesFlag interactItemframesFlag = zone.getFlag(FlagTypes.INTERACT_ITEMFRAMES_FLAG_TYPE).orElse(new InteractItemframesFlag());
        interactItemframesFlag.setEnabled(enable);
        zone.setFlag(interactItemframesFlag);
        try {
            zone.save();
            commandContext.sendMessage(Component.text("Saved!"));
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Component.text("Could not save because " + ce.getMessage()));
        }
        return CommandResult.success();
    }

}