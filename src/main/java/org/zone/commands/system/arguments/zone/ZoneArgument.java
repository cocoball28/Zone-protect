package org.zone.commands.system.arguments.zone;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.zone.ZonePlugin;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.ParseCommandArgument;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;
import org.zone.region.Zone;
import org.zone.region.group.Group;
import org.zone.region.group.SimpleGroup;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ZoneArgument implements CommandArgument<Zone> {

    public static class ZoneArgumentPropertiesBuilder {

        private ParseCommandArgument<Zone> subZoneTo;
        private boolean onlyMainZones = true;
        private String bypassSuggestionPermission;
        private Group level = SimpleGroup.OWNER;

        public String getBypassSuggestionPermission() {
            return this.bypassSuggestionPermission;
        }

        public ZoneArgumentPropertiesBuilder setBypassSuggestionPermission(String bypassSuggestionPermission) {
            this.bypassSuggestionPermission = bypassSuggestionPermission;
            return this;
        }

        public ParseCommandArgument<Zone> getSubZoneTo() {
            return this.subZoneTo;
        }

        public ZoneArgumentPropertiesBuilder setSubZoneTo(ParseCommandArgument<Zone> subZoneTo) {
            this.subZoneTo = subZoneTo;
            return this;
        }

        public boolean isOnlyMainZones() {
            return this.onlyMainZones;
        }

        public ZoneArgumentPropertiesBuilder setOnlyMainZones(boolean onlyMainZones) {
            this.onlyMainZones = onlyMainZones;
            return this;
        }

        public Group getLevel() {
            return this.level;
        }

        public ZoneArgumentPropertiesBuilder setLevel(Group level) {
            this.level = level;
            return this;
        }
    }

    private final @NotNull ZoneArgumentPropertiesBuilder builder;
    private final @NotNull String id;

    public ZoneArgument(@NotNull String id, @NotNull ZoneArgumentPropertiesBuilder builder) {
        this.id = id;
        this.builder = builder;
    }

    @Override
    public @NotNull String getId() {
        return this.id;
    }

    @Override
    public CommandArgumentResult<Zone> parse(CommandContext context, CommandArgumentContext<Zone> argument) throws IOException {
        Stream<Zone> zones = ZonePlugin.getZonesPlugin().getZoneManager().getZones().stream();
        if (this.builder.isOnlyMainZones()) {
            zones = zones.filter(zone -> zone.getParent().isEmpty());
        }
        if (this.builder.getLevel()!=null) {
            Subject subject = context.getSource();
            if (this.builder.getBypassSuggestionPermission()==null || this.builder.getBypassSuggestionPermission()!=null &&
                    !subject.hasPermission(this.builder.getBypassSuggestionPermission())) {
                if (subject instanceof Player player) {
                    zones = zones.filter(zone -> {
                        Group group = zone.getMembers().getGroup(player.uniqueId());
                        return group.inherits(this.builder.getLevel());
                    });
                }
            }
        }
        Zone result = zones
                .filter(zone -> zone.getId().equalsIgnoreCase(argument.getFocusArgument()))
                .findAny()
                .orElseThrow(() -> new IOException("Could not find zone of " + argument.getFocusArgument()));
        return CommandArgumentResult.from(argument, result);
    }

    @Override
    public Collection<CommandCompletion> suggest(CommandContext context, CommandArgumentContext<Zone> argument) {
        Stream<Zone> zones = ZonePlugin.getZonesPlugin().getZoneManager().getZones().stream();
        if (this.builder.isOnlyMainZones()) {
            zones = zones.filter(zone -> zone.getParent().isEmpty());
        }
        if (this.builder.getLevel()!=null) {
            Subject subject = context.getSource();
            if (this.builder.getBypassSuggestionPermission()==null || this.builder.getBypassSuggestionPermission()!=null &&
                    !subject.hasPermission(this.builder.getBypassSuggestionPermission())) {
                if (subject instanceof Player player) {
                    zones = zones.filter(zone -> {
                        Group group = zone.getMembers().getGroup(player.uniqueId());
                        return group.inherits(this.builder.getLevel());
                    });
                }
            }
        }
        return zones
                .filter(zone -> zone.getId().toLowerCase().startsWith(argument.getFocusArgument().toLowerCase()))
                .map(zone -> CommandCompletion.of(zone.getId(), Component.text(zone.getName())))
                .collect(Collectors.toSet());
    }
}
