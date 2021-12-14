package org.zone.commands.structure.create;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.math.vector.Vector3i;
import org.zone.Permissions;
import org.zone.ZonePlugin;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.NotEnoughArgumentsException;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.event.listener.PlayerListener;
import org.zone.region.Zone;
import org.zone.region.ZoneBuilder;
import org.zone.region.flag.meta.member.MembersFlag;
import org.zone.region.group.DefaultGroups;
import org.zone.region.regions.BoundedRegion;
import org.zone.region.regions.Region;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ZoneCreateEndCommand implements ArgumentCommand {
    @Override
    public List<CommandArgument<?>> getArguments() {
        return Arrays.asList(new ExactArgument("create"), new ExactArgument("bounds"), new ExactArgument("end"));
    }

    @Override
    public Component getDescription() {
        return Component.text("Ends the creation by bounds. Will use your location as ending");
    }

    @Override
    public Optional<String> getPermissionNode() {
        return Optional.of(Permissions.REGION_CREATE_BOUNDS.getPermission());
    }

    @Override
    public boolean hasPermission(CommandCause source) {
        if(!(source.subject() instanceof Player)){
            return false;
        }
        return ArgumentCommand.super.hasPermission(source);
    }

    @Override
    public CommandResult run(CommandContext context, String... args) throws NotEnoughArgumentsException {
        Subject subject = context.getSource();
        if (!(subject instanceof Player player)) {
            return CommandResult.error(Component.text("Player only command"));
        }
        Optional<ZoneBuilder> opZone = ZonePlugin.getZonesPlugin().getMemoryHolder().getZoneBuilder(player.uniqueId());
        if (opZone.isEmpty()) {
            return CommandResult.error(Component.text("A region needs to be started. Use /zone create bounds " +
                    "<name...>"));
        }

        Zone zone = opZone.get().build();
        MembersFlag membersFlag = zone.getMembers();
        membersFlag.addMember(DefaultGroups.OWNER, player.uniqueId());
        zone.addFlag(membersFlag);

        if (zone.getParentId().isPresent()) {
            Optional<Zone> opParent = zone.getParent();
            if (opParent.isEmpty()) {
                return CommandResult.error(Component.text("Could not find parent zone of " + zone.getParentId().get()));
            }
            Region region = zone.getRegion();
            if (region instanceof BoundedRegion bounded) {
                Vector3i min = bounded.getMin();
                if (!opParent.get().inRegion(null, min.toDouble())) {
                    return CommandResult.error(Component.text("Region must be within " + opParent.get().getId()));
                }

                Vector3i max = bounded.getMax();
                if (!opParent.get().inRegion(null, max.toDouble())) {
                    return CommandResult.error(Component.text("Region must be within " + opParent.get().getId()));
                }
            }
        }

        ZonePlugin.getZonesPlugin().getZoneManager().register(zone);
        player.sendMessage(Component.text("Created a new zone of ").append(Component.text(zone.getName()).color(NamedTextColor.AQUA)));
        ZonePlugin.getZonesPlugin().getMemoryHolder().unregisterZoneBuilder(player.uniqueId());
        Region region = zone.getRegion();
        if (region instanceof BoundedRegion r) {
            PlayerListener.runOnOutside(r, player.location().blockY() + 3, player::resetBlockChange,
                    zone.getParent().isPresent());
        }

        try {
            zone.save();
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return CommandResult.error(Component.text("Error when saving: " + e.getMessage()));
        }

        return CommandResult.success();
    }
}
