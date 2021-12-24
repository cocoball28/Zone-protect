package org.zone.commands.system.arguments.zone;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ZoneArgument implements CommandArgument<Zone> {

    private final @NotNull ZoneArgumentPropertiesBuilder builder;
    private final @NotNull String id;

    public static class ZoneArgumentPropertiesBuilder {

        private @Nullable ParseCommandArgument<Zone> subZoneTo;
        private boolean onlyMainZones = true;
        private @Nullable String bypassSuggestionPermission;
        private @Nullable GroupKey level = GroupKeys.OWNER;

        public @Nullable String getBypassSuggestionPermission() {
            return this.bypassSuggestionPermission;
        }

        public ZoneArgumentPropertiesBuilder setBypassSuggestionPermission(String bypassSuggestionPermission) {
            this.bypassSuggestionPermission = bypassSuggestionPermission;
            return this;
        }

        public @Nullable ParseCommandArgument<Zone> getSubZoneTo() {
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

        public @Nullable GroupKey getLevel() {
            return this.level;
        }

        public ZoneArgumentPropertiesBuilder setLevel(@Nullable GroupKey level) {
            this.level = level;
            return this;
        }
    }

    public ZoneArgument(@NotNull String id) {
        this(id, new ZoneArgumentPropertiesBuilder());
    }

    public ZoneArgument(@NotNull String id, @NotNull ZoneArgumentPropertiesBuilder builder) {
        this.id = id;
        this.builder = builder;
    }

    @Override
    public @NotNull String getId() {
        return this.id;
    }

    @Override
    public boolean canApply(@NotNull CommandContext context) {
        return !this.suggest(context, "").isEmpty();
    }

    @Override
    public CommandArgumentResult<Zone> parse(@NotNull CommandContext context,
                                             @NotNull CommandArgumentContext<Zone> argument) throws
            IOException {
        Stream<Zone> zones = ZonePlugin.getZonesPlugin().getZoneManager().getZones().stream();
        if (this.builder.isOnlyMainZones()) {
            zones = zones.filter(zone -> zone.getParent().isEmpty());
        }
        if (this.builder.getLevel() != null) {
            Subject subject = context.getSource();
            if (this.builder.getBypassSuggestionPermission() == null ||
                    this.builder.getBypassSuggestionPermission() != null &&
                            !subject.hasPermission(this.builder.getBypassSuggestionPermission())) {
                if (subject instanceof Player player) {
                    zones = zones.filter(zone -> {
                        Group group = zone.getMembers().getGroup(player.uniqueId());
                        if (this.builder.getLevel() == null) {
                            return true;
                        }
                        return group.getAllKeys().contains(this.builder.getLevel());
                    });
                }
            }
        }
        Zone result = zones
                .filter(zone -> zone.getId().equalsIgnoreCase(argument.getFocusArgument()))
                .findAny()
                .orElseThrow(() -> new IOException("Could not find zone of " +
                                                           argument.getFocusArgument()));
        return CommandArgumentResult.from(argument, result);
    }

    @Override
    public Collection<CommandCompletion> suggest(@NotNull CommandContext context,
                                                 @NotNull CommandArgumentContext<Zone> argument) {
        return this.suggest(context, argument.getFocusArgument());
    }

    private Collection<CommandCompletion> suggest(@NotNull CommandContext context,
                                                  @NotNull String focus) {
        Collection<Zone> collection = ZonePlugin.getZonesPlugin().getZoneManager().getZones();
        Stream<Zone> zones = collection.stream();
        if (this.builder.isOnlyMainZones()) {
            zones = zones.filter(zone -> zone.getParent().isEmpty());
        }
        if (this.builder.getLevel() != null) {
            Subject subject = context.getSource();
            if (this.builder.getBypassSuggestionPermission() == null ||
                    this.builder.getBypassSuggestionPermission() != null &&
                            !subject.hasPermission(this.builder.getBypassSuggestionPermission())) {
                if (subject instanceof Player player) {
                    zones = zones.filter(zone -> {
                        Group group = zone.getMembers().getGroup(player.uniqueId());
                        if (this.builder.getLevel() == null) {
                            return true;
                        }
                        return group.contains(this.builder.getLevel());
                    });
                }
            }
        }
        return zones
                .filter(zone -> zone.getId().toLowerCase().startsWith(focus.toLowerCase()))
                .map(zone -> CommandCompletion.of(zone.getId(), Component.text(zone.getName())))
                .collect(Collectors.toSet());
    }
}
