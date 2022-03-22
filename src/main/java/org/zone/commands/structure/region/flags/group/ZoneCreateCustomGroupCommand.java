package org.zone.commands.structure.region.flags.group;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.ConfigurateException;
import org.zone.ZonePlugin;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.simple.StringArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.arguments.zone.ZoneGroupArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.permissions.ZonePermissions;
import org.zone.region.Zone;
import org.zone.region.flag.meta.member.MembersFlag;
import org.zone.region.group.Group;
import org.zone.region.group.SimpleGroup;
import org.zone.utils.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneCreateCustomGroupCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE_ID = new ZoneArgument("zoneId",
            new ZoneArgument.ZoneArgumentPropertiesBuilder().setBypassSuggestionPermission(
                    ZonePermissions.OVERRIDE_CREATE_CUSTOM_GROUP));
    @SuppressWarnings("allow-string-argument")
    public static final StringArgument KEY = new StringArgument("key");
    public static final ZoneGroupArgument PARENT = new ZoneGroupArgument("parent", ZONE_ID);

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("region"),
                new ExactArgument("create"),
                new ExactArgument("group"),
                ZONE_ID,
                KEY,
                PARENT);
    }

    @Override
    public @NotNull Component getDescription() {
        return Messages.getCreateCustomGroupCommandDescription();
    }

    @Override
    public @NotNull Optional<ZonePermission> getPermissionNode() {
        return Optional.of(ZonePermissions.CREATE_CUSTOM_GROUP);
    }

    @Override
    public @NotNull CommandResult run(
            @NotNull CommandContext commandContext, @NotNull String... args) {
        Zone zone = commandContext.getArgument(this, ZONE_ID);
        String key = commandContext.getArgument(this, KEY);
        Group parent = commandContext.getArgument(this, PARENT);
        Group group = new SimpleGroup(ZonePlugin.getZonesPlugin().getPluginContainer(),
                key,
                parent);
        MembersFlag membersFlag = zone.getMembers();
        membersFlag.registerGroup(group);
        try {
            zone.save();
            commandContext.sendMessage(Messages.getCreatedGroup(group, parent));
        } catch (ConfigurateException ce) {
            ce.printStackTrace();
            return CommandResult.error(Messages.getZoneSavingError(ce));
        }
        return CommandResult.success();
    }
}
