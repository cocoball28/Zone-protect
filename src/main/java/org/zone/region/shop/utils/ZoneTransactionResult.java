package org.zone.region.shop.utils;

import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.account.Account;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.service.economy.transaction.TransactionResult;
import org.spongepowered.api.service.economy.transaction.TransactionType;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ZoneTransactionResult implements TransactionResult {

    private final Account account;
    private final Currency currency;
    private final BigDecimal amount;
    private Set<Context> context;
    private final ResultType resultType;
    private final TransactionType transactionType;

    public ZoneTransactionResult(TransactionResultBuilder builder) {
        this.account = builder.getAccount();
        if (this.account == null) {
            throw new IllegalStateException("Account was not set on builder");
        }
        this.currency = builder.getCurrency();
        if (this.currency == null) {
            throw new IllegalStateException("Currency was not set on builder");
        }
        this.amount = builder.getAmount();
        if (this.amount == null) {
            throw new IllegalStateException("Amount was not set on builder");
        }
        this.context = builder.getContext();
        if (this.context == null) {
            this.context = new HashSet<>();
        }
        this.resultType = builder.getResultType();
        if (this.resultType == null) {
            throw new IllegalStateException("ResultType was not set on builder");
        }
        this.transactionType = builder.getTransactionType();
        if (this.transactionType == null) {
            throw new IllegalStateException("TransactionType was not set on builder");
        }
    }

    @Override
    public Account account() {
        return this.account;
    }

    @Override
    public Currency currency() {
        return this.currency;
    }

    @Override
    public BigDecimal amount() {
        return this.amount;
    }

    @Override
    public Set<Context> contexts() {
        return this.context;
    }

    @Override
    public ResultType result() {
        return this.resultType;
    }

    @Override
    public TransactionType type() {
        return this.transactionType;
    }
}
