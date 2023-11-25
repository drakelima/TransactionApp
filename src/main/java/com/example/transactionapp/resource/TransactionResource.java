package com.example.transactionapp.resource;

import com.example.transactionapp.dto.TransactionDTO;
import com.example.transactionapp.model.Transaction;
import com.example.transactionapp.service.CurrencyConverterService;
import com.example.transactionapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transactions")
public class TransactionResource {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CurrencyConverterService currencyConverterService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.saveTransaction(transaction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.retrieveTransaction(id));
    }

    @GetMapping("/{id}/convert")
    public ResponseEntity<TransactionDTO> convertTransactionCurrency(@PathVariable Long id,
                                                                     @RequestParam String toCurrency) {

        Transaction transaction = transactionService.retrieveTransaction(id);

        return ResponseEntity.ok(currencyConverterService.convertCurrencyTransaction(transaction, toCurrency));
    }
}
