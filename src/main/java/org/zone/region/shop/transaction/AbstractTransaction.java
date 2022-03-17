package org.zone.region.shop.transaction;

import org.zone.region.flag.meta.eco.balance.BalanceFlag;

import java.math.BigDecimal;

public class AbstractTransaction implements Transaction {

    private final BigDecimal original;
    private final BigDecimal after;
    private final BalanceFlag flag;
    private final TransactionState state;

    public AbstractTransaction(TransactionBuilder builder) {
        this.after = builder.getAfter();
        this.original = builder.getOriginal();
        this.flag = builder.getFlag();
        this.state = builder.getState();
    }

    @Override
    public BigDecimal getOriginal() {
        return this.original;
    }

    @Override
    public BigDecimal getAfter() {
        return this.after;
    }

    @Override
    public BalanceFlag getFlag() {
        return this.flag;
    }

    @Override
    public TransactionState getState() {
        return this.state;
    }
}
