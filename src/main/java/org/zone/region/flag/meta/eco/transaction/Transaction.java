package org.zone.region.flag.meta.eco.transaction;

import org.zone.region.flag.meta.eco.EcoFlag;

import java.math.BigDecimal;

public interface Transaction {

    BigDecimal getOriginal();
    BigDecimal getAfter();
    EcoFlag getFlag();
    TransactionState getState();
}
