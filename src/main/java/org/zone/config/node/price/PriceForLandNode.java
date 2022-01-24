package org.zone.config.node.price;

import net.kyori.adventure.text.Component;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.zone.ZonePlugin;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.operation.AnyMatchArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.config.ZoneConfig;
import org.zone.config.command.ConfigCommandNode;
import org.zone.config.node.ZoneNode;
import org.zone.region.flag.meta.eco.price.Price;
import org.zone.region.flag.meta.eco.price.player.PlayerEcoPrice;
import org.zone.region.flag.meta.eco.price.player.PlayerExpPrice;
import org.zone.region.flag.meta.eco.price.player.PlayerLevelPrice;
import org.zone.region.flag.meta.eco.price.zone.ZoneEcoPrice;
import org.zone.region.flag.meta.eco.price.zone.ZonePowerPrice;
import org.zone.utils.Messages;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class PriceForLandNode implements ZoneNode<Price<?>> {

    class TypeConfigCommandNode implements ConfigCommandNode<Class<? extends Price<?>>> {

        @Override
        public String getDisplayId() {
            return "zone.region.claim.price.type";
        }

        @Override
        public CommandArgument<Class<? extends Price<?>>> getCommandArgument() {
            return new AnyMatchArgument<>("type",
                    clazz -> {
                        String clazzName = clazz.getSimpleName();
                        if (clazzName.startsWith("Player")) {
                            clazzName.substring(6);
                        } else if (clazzName.startsWith("Zone")) {
                            clazzName.substring(4);
                        }
                        return clazzName.substring(0, clazzName.length() - 5);
                    },
                    PlayerEcoPrice.class,
                    PlayerExpPrice.class,
                    PlayerLevelPrice.class,
                    ZoneEcoPrice.class,
                    ZonePowerPrice.class);
        }

        @Override
        public CommandResult onChange(
                CommandContext context, Class<? extends Price<?>> newValue) {
            ZoneConfig config = ZonePlugin.getZonesPlugin().getConfig();
            try {
                if (newValue.equals(ZonePowerPrice.class)) {
                    PriceForLandNode.this.set(config, new ZonePowerPrice(0));
                    return CommandResult.success();
                }
            } catch (SerializationException e) {
                e.printStackTrace();
                return CommandResult.error(Messages.getZoneSavingError(e));
            }
            return CommandResult.error(Component.text("This message should never be seen by the " +
                    "player: Unknown price type of " +
                    newValue.getSimpleName() +
                    " however " +
                    "found in argument"));
        }
    }

    @Override
    public Object[] getNode() {
        return new Object[]{"zone", "region", "claim", "price", "perBlock"};
    }

    @Override
    public Price<?> getInitialValue() {
        return new ZonePowerPrice(0);
    }

    @Override
    public Collection<ConfigCommandNode<?>> getNodes() {
        return List.of(new TypeConfigCommandNode());
    }

    @Override
    public void set(
            CommentedConfigurationNode node, Price<?> price) throws SerializationException {

    }

    @Override
    public Optional<Price<?>> get(CommentedConfigurationNode node) {
        return Optional.empty();
    }
}
