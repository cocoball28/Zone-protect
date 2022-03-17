package org.zone.region.shop.transaction;

import org.zone.region.flag.meta.eco.balance.BalanceFlag;

import java.math.BigDecimal;

/**
 * Builder used for building Transactions
 */
public class TransactionBuilder {

    private BigDecimal original;
    private BigDecimal after;
    private BalanceFlag flag;
    private TransactionState state;

    public BigDecimal getOriginal() {
        return this.original;
    }

    public TransactionBuilder setOriginal(BigDecimal original) {
        this.original = original;
        return this;
    }

    public BigDecimal getAfter() {
        return this.after;
    }

    public TransactionBuilder setAfter(BigDecimal after) {
        this.after = after;
        return this;
    }

    public BalanceFlag getFlag() {
        return this.flag;
    }

    public TransactionBuilder setFlag(BalanceFlag flag) {
        this.flag = flag;
        return this;
    }

    public TransactionState getState() {
        return this.state;
    }

    public TransactionBuilder setState(TransactionState state) {
        this.state = state;
        return this;
    }
}
