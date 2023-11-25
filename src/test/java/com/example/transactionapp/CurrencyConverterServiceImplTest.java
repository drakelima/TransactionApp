package com.example.transactionapp;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.example.transactionapp.dto.TransactionDTO;
import com.example.transactionapp.model.Transaction;
import com.example.transactionapp.service.impl.CurrencyConverterServiceImpl;
import com.google.gson.JsonParser;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class CurrencyConverterServiceImplTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CurrencyConverterServiceImpl currencyConverterService;

    @Test
    public void whenConvertCurrencyTransaction_thenSuccess() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setPurchaseAmount(new BigDecimal("100"));
        transaction.setTransactionDate(LocalDate.of(2001, 3, 31));
        transaction.setUniqueIdentifier("trans002");

        String responseBody = "{\"data\":[{\"country_currency_desc\":\"Brazil-Real\",\"exchange_rate\":\"2.043\",\"record_date\":\"2001-03-31\"}],\"meta\":{\"count\":1,\"labels\":{\"country_currency_desc\":\"Country - Currency Description\",\"exchange_rate\":\"Exchange Rate\",\"record_date\":\"Record Date\"},\"dataTypes\":{\"country_currency_desc\":\"STRING\",\"exchange_rate\":\"NUMBER\",\"record_date\":\"DATE\"},\"dataFormats\":{\"country_currency_desc\":\"String\",\"exchange_rate\":\"10.2\",\"record_date\":\"YYYY-MM-DD\"},\"total-count\":1,\"total-pages\":1},\"links\":{\"self\":\"&page%5Bnumber%5D=1&page%5Bsize%5D=100\",\"first\":\"&page%5Bnumber%5D=1&page%5Bsize%5D=100\",\"prev\":null,\"next\":null,\"last\":\"&page%5Bnumber%5D=1&page%5Bsize%5D=100\"}}";

        when(restTemplate.getForEntity(any(String.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>(responseBody, HttpStatus.OK));

        TransactionDTO result = currencyConverterService.convertCurrencyTransaction(transaction, "Brazil-Real");

        assertNotNull(result);
        assertEquals(transaction.getUniqueIdentifier(), result.getUniqueIdentifier());
    }

    @Test
    public void whenGetExchangeRate_thenNoDataAvailable() {
        // Arrange
        LocalDate transactionDate = LocalDate.of(2023, 1, 1);
        String toCurrency = "EUR";
        when(restTemplate.getForEntity(any(String.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>("{}", HttpStatus.OK)); // Empty data response

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                currencyConverterService.getExchangeRate(toCurrency, transactionDate));

        String expectedMessage = "No exchange rate data available";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    // Additional tests for other scenarios
}
