package com.example.transactionapp.service.impl;

import com.example.transactionapp.dto.TransactionDTO;
import com.example.transactionapp.model.Transaction;
import com.example.transactionapp.service.CurrencyConverterService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Service
public class CurrencyConverterServiceImpl implements CurrencyConverterService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public TransactionDTO convertCurrencyTransaction(Transaction transaction, String toCurrency) {
        BigDecimal convertedAmount = convertCurrency(
                toCurrency,
                transaction.getPurchaseAmount(),
                transaction.getTransactionDate());

        return new TransactionDTO()
                .setUniqueIdentifier(transaction.getUniqueIdentifier())
                .setDescription(transaction.getDescription())
                .setTransactionDate(transaction.getTransactionDate())
                .setOriginalAmount(transaction.getPurchaseAmount())
                .setExchangeRate(getExchangeRate(toCurrency, transaction.getTransactionDate()))
                .setConvertedAmount(convertedAmount);
    }

    private BigDecimal convertCurrency(String toCurrency, BigDecimal amount, LocalDate transactionDate) {
        BigDecimal exchangeRate = getExchangeRate(toCurrency, transactionDate);
        return amount.multiply(exchangeRate);
    }

    public BigDecimal getExchangeRate(String toCurrency, LocalDate transactionDate) {
        LocalDate startDate = transactionDate.minusMonths(6);
        String urlTemplate =
                "https://api.fiscaldata.treasury.gov/services/api/fiscal_service/v1/accounting/od/rates_of_exchange" +
                "?fields=country_currency_desc,exchange_rate,record_date" +
                "&filter=record_date:gte:" + startDate + ",record_date:lte:" + transactionDate +
                ",country_currency_desc:in:(" + toCurrency + ")";

        ResponseEntity<String> response = restTemplate.getForEntity(urlTemplate, String.class);
        JsonElement root = JsonParser.parseString(Objects.requireNonNull(response.getBody()));
        JsonElement ratesElement = root.getAsJsonObject().get("data");

        BigDecimal latestRate = null;
        LocalDate latestRateDate = startDate;

        if (ratesElement != null && ratesElement.isJsonArray()) {
            for (JsonElement rateElement : ratesElement.getAsJsonArray()) {
                JsonObject rateObject = rateElement.getAsJsonObject();
                LocalDate rateDate = LocalDate.parse(rateObject.get("record_date").getAsString());
                if ((rateDate.isAfter(latestRateDate) || rateDate.isEqual(latestRateDate)) &&
                        rateDate.isBefore(transactionDate.plusDays(1))) {
                    latestRateDate = rateDate;
                    latestRate = rateObject.get("exchange_rate").getAsBigDecimal();
                }
            }
        }

        if (latestRate == null) {
            throw new IllegalArgumentException("No exchange rate data available for the specified currency and date " +
                    "within the last 6 months");
        }

        return latestRate;
    }

}
