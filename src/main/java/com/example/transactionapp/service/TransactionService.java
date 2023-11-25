package com.example.transactionapp.service;

import com.example.transactionapp.model.Transaction;

public interface TransactionService {
    Transaction saveTransaction(Transaction transaction);
    Transaction retrieveTransaction(Long id);
}
