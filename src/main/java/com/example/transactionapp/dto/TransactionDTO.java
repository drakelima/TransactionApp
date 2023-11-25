package com.example.transactionapp.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class TransactionDTO {
    private String uniqueIdentifier;
    private String description;
    private LocalDate transactionDate;
    private BigDecimal originalAmount;
    private BigDecimal exchangeRate;
    private BigDecimal convertedAmount;

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public TransactionDTO setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TransactionDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public TransactionDTO setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public TransactionDTO setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
        return this;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public TransactionDTO setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
        return this;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public TransactionDTO setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount.setScale(2, RoundingMode.HALF_EVEN);
        return this;
    }
}
