package org.zone.region.shop.transaction.price;

import org.spongepowered.api.service.economy.Currency;
import org.zone.region.shop.transaction.price.player.PlayerEcoPrice;
import org.zone.region.shop.transaction.price.player.PlayerExpPrice;
import org.zone.region.shop.transaction.price.player.PlayerLevelPrice;
import org.zone.region.shop.transaction.price.zone.ZoneEcoPrice;
import org.zone.region.shop.transaction.price.zone.ZonePowerPrice;

import java.math.BigDecimal;

/**
 * A helper class for creating prices
 *
 * @since 1.0.1
 */
public class PriceBuilder {

    private double amount;
    private Currency currency;
    private PriceType type;

    public PriceBuilder setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public PriceBuilder setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public PriceBuilder setType(PriceType type) {
        this.type = type;
        return this;
    }

    public Price.ZonePrice<?> buildZone() {
        if (this.type == null) {
            throw new RuntimeException("PriceType cannot be null");
        }
        switch (this.type) {
            case ECO -> {
                if (this.currency == null) {
                    throw new RuntimeException("Currency must be specified for a ECO type");
                }
                return new ZoneEcoPrice(this.currency, BigDecimal.valueOf(this.amount));
            }
            case POWER -> {
                if (((long) this.amount) != this.amount) {
                    throw new RuntimeException("Amount must be a whole number");
                }
                return new ZonePowerPrice((long) this.amount);
            }
            default -> throw new RuntimeException("Unknown type for player of " + this.type.name());
        }
    }

    public Price.PlayerPrice<?> buildPlayer() {
        if (this.type == null) {
            throw new RuntimeException("PriceType cannot be null");
        }
        switch (this.type) {
            case ECO -> {
                if (this.currency == null) {
                    throw new RuntimeException("Currency must be specified for a ECO type");
                }
                return new PlayerEcoPrice(this.currency, BigDecimal.valueOf(this.amount));
            }
            case EXP -> {
                if (((int) this.amount) != this.amount) {
                    throw new RuntimeException("Amount must be a whole number");
                }
                return new PlayerExpPrice((int) this.amount);
            }
            case LEVEL -> {
                if (((int) this.amount) != this.amount) {
                    throw new RuntimeException("Amount must be a whole number");
                }
                return new PlayerLevelPrice((int) this.amount);
            }
            default -> throw new RuntimeException("Unknown type for player of " + this.type.name());
        }
    }

}
