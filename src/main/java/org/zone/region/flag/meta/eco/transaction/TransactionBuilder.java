package org.zone.region.flag.meta.eco.transaction;

import org.zone.region.flag.meta.eco.EcoFlag;

import java.math.BigDecimal;

public class TransactionBuilder {

    private BigDecimal original;
    private BigDecimal after;
    private EcoFlag flag;
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

    public EcoFlag getFlag() {
        return this.flag;
    }

    public TransactionBuilder setFlag(EcoFlag flag) {
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
