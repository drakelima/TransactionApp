package com.example.transactionapp.service.impl;

import com.example.transactionapp.model.Transaction;
import com.example.transactionapp.repository.TransactionRepository;
import com.example.transactionapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction retrieveTransaction(Long id) {
        return transactionRepository.findById(id).orElseThrow(/* Exception handling */);
    }
}
