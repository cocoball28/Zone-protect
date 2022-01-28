package org.zone.config.node.price;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.registry.RegistryEntry;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.zone.ZonePlugin;
import org.zone.commands.system.CommandArgument;
import org.zone.commands.system.arguments.simple.EnumArgument;
import org.zone.commands.system.context.CommandContext;
import org.zone.config.ZoneConfig;
import org.zone.config.command.ConfigCommandNode;
import org.zone.config.node.ZoneNode;
import org.zone.region.flag.meta.eco.price.Price;
import org.zone.region.flag.meta.eco.price.PriceBuilder;
import org.zone.region.flag.meta.eco.price.PriceType;
import org.zone.region.flag.meta.eco.price.zone.ZonePowerPrice;
import org.zone.utils.Messages;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class PriceForLandNode implements ZoneNode<Price<?, ?>> {

    class TypeConfigCommandNode implements ConfigCommandNode<PriceType> {

        @Override
        public String getDisplayId() {
            return "zone.region.claim.price.type";
        }

        @Override
        public CommandArgument<PriceType> getCommandArgument() {
            return new EnumArgument<>("type", PriceType.class);
        }

        @Override
        public CommandResult onChange(
                CommandContext context, PriceType newValue) {
            ZoneConfig config = ZonePlugin.getZonesPlugin().getConfig();
            Optional<Currency> opCurrency =
                    Sponge.serviceProvider().provide(EconomyService.class).map(EconomyService::defaultCurrency);
            try {
                PriceBuilder builder =
                        new PriceBuilder()
                                .setType(newValue)
                                .setAmount(0)
                                .setCurrency(opCurrency.orElse(null));
                try {
                    PriceForLandNode.this.set(config, builder.buildPlayer());
                }catch (RuntimeException e){
                    PriceForLandNode.this.set(config, builder.buildZone());
                }
                return CommandResult.success();
            } catch (SerializationException e) {
                e.printStackTrace();
                return CommandResult.error(Messages.getZoneSavingError(e));
            }
        }
    }

    @Override
    public Object[] getNode() {
        return new Object[]{"zone", "region", "claim", "price", "perBlock"};
    }

    @Override
    public ZonePowerPrice getInitialValue() {
        return new ZonePowerPrice(0);
    }

    @Override
    public Collection<ConfigCommandNode<?>> getNodes() {
        return List.of(new TypeConfigCommandNode());
    }

    @Override
    public void set(CommentedConfigurationNode node,
            Price<?, ?> price) throws SerializationException {
            node.node("type").set(price.getType().name());
            node.node("amount").set(price.getAmount().doubleValue());
            if(price instanceof Price.EcoPrice){
                node.node("currency").set(((Price.EcoPrice<?>)price).getCurrency());
            }
    }

    @Override
    public Optional<Price<?, ?>> get(CommentedConfigurationNode node) {
        String priceTypeString = node.node("type").getString();
        double amount = node.node("amount").getDouble();
        String currencyString = node.node("currency").getString();
        if(priceTypeString == null){
            return Optional.empty();
        }
        PriceType type;
        try{
            type = PriceType.valueOf(priceTypeString);
        }catch (IllegalArgumentException e){
            return Optional.empty();
        }

        Currency currency = null;
        if(currencyString != null){
            currency =
                    RegistryTypes.CURRENCY.get().findEntry(ResourceKey.resolve(currencyString)).map(
                            RegistryEntry::value).orElse(null);
        }

        PriceBuilder builder =
                new PriceBuilder().setCurrency(currency).setAmount(amount).setType(type);

        try{
            return Optional.of(builder.buildPlayer());
        }catch (RuntimeException e){
            return Optional.of(builder.buildZone());
        }
    }
}
