package com.example.transactionapp.service;

import com.example.transactionapp.dto.TransactionDTO;
import com.example.transactionapp.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CurrencyConverterService {
    TransactionDTO convertCurrencyTransaction(Transaction transaction, String toCurrency);
}
