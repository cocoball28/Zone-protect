package org.zone.region.shop.transaction;

import org.zone.region.flag.meta.eco.balance.BalanceFlag;

import java.math.BigDecimal;

/**
 * A transaction between a something to/from a EcoFlag
 *
 * @since 1.0.1
 */
public interface Transaction {

    /**
     * Gets the original amount found within the EcoFlag
     *
     * @return the original balance
     * @since 1.0.1
     */
    BigDecimal getOriginal();

    /**
     * Gets the new amount to come from the EcoFlag
     *
     * @return the new balance
     * @since 1.0.1
     */
    BigDecimal getAfter();

    /**
     * Gets the eco flag money is being transferred to/from
     *
     * @return The flag to use
     * @since 1.0.1
     */
    BalanceFlag getFlag();

    /**
     * Gets the state of the transaction
     *
     * @return The state of the transaction
     * @since 1.0.1
     */
    TransactionState getState();
}
