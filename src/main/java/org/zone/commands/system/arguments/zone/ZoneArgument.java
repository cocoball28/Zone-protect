package org.zone.commands.system.arguments.zone;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.permission.Subject;
import org.zone.Identifiable;
import org.zone.ZonePlugin;
import org.zone.commands.system.CommandArgumentResult;
import org.zone.commands.system.GUICommandArgument;
import org.zone.commands.system.ParseCommandArgument;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilter;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilterBuilder;
import org.zone.commands.system.arguments.zone.filter.ZoneArgumentFilters;
import org.zone.commands.system.context.CommandArgumentContext;
import org.zone.commands.system.context.CommandContext;
import org.zone.permissions.ZonePermission;
import org.zone.region.Zone;
import org.zone.region.group.key.GroupKey;
import org.zone.region.group.key.GroupKeys;
import org.zone.utils.Messages;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Gets a zone from the command
 */
public class ZoneArgument implements GUICommandArgument<Zone> {

    private final @NotNull Collection<? extends ZoneArgumentFilter> filters;
    private final @Nullable ZonePermission bypassPermission;
    private final @NotNull String id;

    @Deprecated(since = "1.0.1", forRemoval = true)
    public static class ZoneArgumentPropertiesBuilder {

        public boolean isVisitor;
        private @Nullable ParseCommandArgument<Zone> subZoneTo;
        private boolean onlyMainZones = true;
        private boolean onlyPartOf;
        private @Nullable ZonePermission bypassSuggestionPermission;
        private @Nullable GroupKey level = GroupKeys.OWNER;

        public boolean isLimitedToOnlyPartOf() {
            return this.onlyPartOf;
        }

        public ZoneArgumentPropertiesBuilder setLimitedToOnlyPartOf(boolean check) {
            this.onlyPartOf = check;
            return this;
        }

        public @Nullable ZonePermission getBypassSuggestionPermission() {
            return this.bypassSuggestionPermission;
        }

        public ZoneArgumentPropertiesBuilder setBypassSuggestionPermission(ZonePermission bypassSuggestionPermission) {
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

        public ZoneArgumentPropertiesBuilder setVisitorOnly(boolean value) {
            this.isVisitor = value;
            return this;
        }

        public Collection<ZoneArgumentFilter> getFilters() {
            Collection<ZoneArgumentFilter> filter = new HashSet<>();
            if (this.isVisitor) {
                filter.add(new ZoneArgumentFilterBuilder()
                        .setFilter(ZoneArgumentFilters.VISITORS_ONLY)
                        .build());
            }
            if (this.onlyMainZones) {
                filter.add(new ZoneArgumentFilterBuilder()
                        .setFilter(ZoneArgumentFilters.MAIN_ONLY)
                        .build());
            }
            if (this.level != null) {
                filter.add(new ZoneArgumentFilterBuilder()
                        .setFilter(ZoneArgumentFilters.withGroupKey(this.level))
                        .build());
            }
            if (this.onlyPartOf) {
                filter.add(new ZoneArgumentFilterBuilder()
                        .setFilter(ZoneArgumentFilters.MEMBERS_ONLY)
                        .build());
            }
            return filter;
        }
    }

    /**
     * Creates a zone argument that will provide any zone
     *
     * @param id The id of the argument
     */
    public ZoneArgument(@NotNull String id) {
        this(id, new ZoneArgumentPropertiesBuilder());
    }

    /**
     * Creates a zone argument that will only allow the properties provided
     *
     * @param id      The id of the argument
     * @param builder The properties to accept
     *
     * @deprecated Removed due to limiting properties, use ZoneArgument(String id, ZonePermission
     * bypass, Collection filters) instead
     */
    @Deprecated(since = "1.0.1", forRemoval = true)
    public ZoneArgument(@NotNull String id, @NotNull ZoneArgumentPropertiesBuilder builder) {
        this(id, builder.getBypassSuggestionPermission(), builder.getFilters());
    }

    public ZoneArgument(
            @NotNull String id, @Nullable ZonePermission bypass, ZoneArgumentFilter... filters) {
        this(id, bypass, Arrays.asList(filters));
    }

    public ZoneArgument(
            @NotNull String id,
            @Nullable ZonePermission bypass,
            @NotNull Collection<? extends ZoneArgumentFilter> filters) {
        this.id = id;
        this.bypassPermission = bypass;
        this.filters = filters;
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
    public CommandArgumentResult<Zone> parse(
            @NotNull CommandContext context, @NotNull CommandArgumentContext<Zone> argument) throws
            IOException {
        Zone result = this
                .getZones(context)
                .filter(zone -> zone.getId().equalsIgnoreCase(argument.getFocusArgument()))
                .findAny()
                .orElseThrow(() -> new IOException("Could not find zone of " +
                        argument.getFocusArgument()));
        return CommandArgumentResult.from(argument, result);
    }

    @Override
    public @NotNull Collection<CommandCompletion> suggest(
            @NotNull CommandContext commandContext,
            @NotNull CommandArgumentContext<Zone> argument) {
        return this.suggest(commandContext, argument.getFocusArgument());
    }

    private ItemStack getItem(Zone zone) {
        return ItemStack
                .builder()
                .quantity(1)
                .itemType(ItemTypes.CAKE)
                .add(Keys.DISPLAY_NAME, Component.text(zone.getName()))
                .add(Keys.LORE, List.of(Component.text(zone.getId()).color(NamedTextColor.AQUA)))
                .build();
    }

    private @NotNull Stream<Zone> getZones(@NotNull CommandContext context) {
        Collection<Zone> collection = ZonePlugin.getZonesPlugin().getZoneManager().getRegistered();
        Stream<Zone> zones = collection.stream();
        for (ZoneArgumentFilter filter : this.filters) {
            @NotNull Subject source = context.getSource();
            zones = zones.filter(zone -> this.bypassPermission != null &&
                    !filter.mustHappen() &&
                    (this.bypassPermission.hasPermission(source) ||
                            (filter
                                    .getPermission()
                                    .map(permission -> permission.hasPermission(source))
                                    .orElse(false))) || filter.canAccept(zone, context));
        }
        return zones;
    }

    private Collection<CommandCompletion> suggest(
            @NotNull CommandContext context, @NotNull String focus) {

        return this
                .getZones(context)
                .filter(zone -> zone.getId().toLowerCase().startsWith(focus.toLowerCase()))
                .map(zone -> CommandCompletion.of(zone.getId(), Messages.getZoneNameInfo(zone)))
                .collect(Collectors.toSet());
    }

    @Override
    public Map<ItemStack, String> createMenuOptions(CommandContext context) {
        return this.getZones(context).collect(Collectors.toMap(this::getItem, Identifiable::getId));
    }
}
