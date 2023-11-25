package com.example.transactionapp.model;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    @Size(max = 50, message = "Description must not exceed 50 characters")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Transaction date cannot be null")
    private LocalDate transactionDate;

    @Column(nullable = false, precision = 10, scale = 2)
    @DecimalMin(value = "0.01", message = "Purchase amount must be a positive number")
    private BigDecimal purchaseAmount;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Unique identifier is required")
    private String uniqueIdentifier;

    public Long getId() {
        return id;
    }

    public Transaction setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Transaction setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public Transaction setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public BigDecimal getPurchaseAmount() {
        return purchaseAmount;
    }

    public Transaction setPurchaseAmount(BigDecimal purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
        return this;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public Transaction setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
        return this;
    }
}
