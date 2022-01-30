package org.zone.region.flag.meta.eco.transaction;

import org.zone.region.flag.meta.eco.balance.BalanceFlag;

import java.math.BigDecimal;

/**
 * A transaction between a something to/from a EcoFlag
 */
public interface Transaction {

    /**
     * Gets the original amount found within the EcoFlag
     *
     * @return the original balance
     */
    BigDecimal getOriginal();

    /**
     * Gets the new amount to come from the EcoFlag
     *
     * @return the new balance
     */
    BigDecimal getAfter();

    /**
     * Gets the eco flag money is being transferred to/from
     *
     * @return The flag to use
     */
    BalanceFlag getFlag();

    /**
     * Gets the state of the transaction
     *
     * @return The state of the transaction
     */
    TransactionState getState();
}
