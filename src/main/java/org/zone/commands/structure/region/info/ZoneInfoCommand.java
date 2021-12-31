package org.zone.commands.structure.region.info;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.user.UserManager;
import org.zone.Permissions;
import org.zone.commands.structure.misc.Messages;
import org.zone.commands.system.ArgumentCommand;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.ExactArgument;
import org.zone.commands.system.arguments.zone.ZoneArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.flag.meta.eco.EcoFlag;
import org.zone.region.flag.meta.member.MembersFlag;
import org.zone.region.group.key.GroupKeys;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ZoneInfoCommand implements ArgumentCommand {

    public static final ZoneArgument ZONE = new ZoneArgument("zoneId");

    @Override
    public @NotNull List<CommandArgument<?>> getArguments() {
        return List.of(new ExactArgument("region"), new ExactArgument("info"), ZONE);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.text("Show info about the zone");
    }

    @Override
    public @NotNull Optional<String> getPermissionNode() {
        return Optional.of(Permissions.REGION_ADMIN_INFO.getPermission());
    }

    @Override
    public @NotNull CommandResult run(CommandContext commandContext, String... args) {
        Zone zone = commandContext.getArgument(this, ZONE);
        MembersFlag membersFlag = zone.getMembers();
        EcoFlag ecoFlag = zone.getEconomy();
        @NotNull Collection<UUID> members = membersFlag.getMembers();

        commandContext.sendMessage(Messages.getZoneInfoCommandZoneName(zone));
        commandContext.sendMessage(Messages.getZoneInfoCommandZoneMembers(members));
        if (Sponge.serviceProvider().provide(EconomyService.class).isPresent()) {
            commandContext.sendMessage(Component.text("Balance: "));
            ecoFlag
                    .getMoney()
                    .forEach((currency, bigDecimal) -> commandContext.sendMessage(Component
                                                                                          .text("- ")
                                                                                          .append(currency.symbol())
                                                                                          .append(Component.text(
                                                                                                  " " +
                                                                                                          bigDecimal.toString()))));
        }


        Collection<UUID> ownerIds = membersFlag
                .getGroupMapping()
                .entrySet()
                .parallelStream()
                .filter(entry -> entry.getKey().contains(GroupKeys.OWNER))
                .flatMap(e -> e.getValue().parallelStream())
                .collect(Collectors.toSet());
        UserManager userManager = Sponge.server().userManager();
        commandContext.sendMessage(Component.text("Owners: "));
        ownerIds.forEach(uuid -> userManager
                .streamAll()
                .filter(profile -> profile.uuid().equals(uuid))
                .forEach(profile -> commandContext.sendMessage(Component.text("- " +
                                                                                      profile
                                                                                              .name()
                                                                                              .orElse("Unknown")))));
        return CommandResult.success();
    }
}
