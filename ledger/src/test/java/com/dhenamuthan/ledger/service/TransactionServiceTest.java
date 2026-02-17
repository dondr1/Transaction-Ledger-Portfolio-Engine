package com.dhenamuthan.ledger.service;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.dhenamuthan.ledger.exception.InsufficientFundsException;
import com.dhenamuthan.ledger.exception.InsufficientHoldingsException;
import com.dhenamuthan.ledger.model.Transaction;
import com.dhenamuthan.ledger.model.TransactionType;
import com.dhenamuthan.ledger.repository.TransactionRepository;

class TransactionServiceTest {

    @Test
    void buyShouldFailWhenInsufficientFunds() {
        TransactionRepository mockRepo = mock(TransactionRepository.class);
        TransactionService service = new TransactionService(mockRepo);

        Transaction deposit = new Transaction(
                1L,
                TransactionType.DEPOSIT,
                "CAD",
                new BigDecimal("100"),
                BigDecimal.ZERO
        );

        when(mockRepo.findByUserId(1L)).thenReturn(List.of(deposit));

        Transaction buy = new Transaction(
                1L,
                TransactionType.BUY,
                "AAPL",
                new BigDecimal("2"),
                new BigDecimal("100")
        );

        assertThrows(InsufficientFundsException.class, () -> service.createTransaction(buy));
    }

    @Test
    void sellShouldFailWhenInsufficientHoldings() {

    TransactionRepository mockRepo=mock(TransactionRepository.class);

    TransactionService service=new TransactionService(mockRepo);

    Transaction buy=new Transaction(
                        1L,
                        TransactionType.BUY,
                        "AAPL",
                        new BigDecimal("1"),
                        new BigDecimal("100"));

    when(mockRepo.findByUserId(1L)).thenReturn(List.of(buy));

    when(mockRepo.findByUserIdAndAsset(1L, "AAPL")).thenReturn(List.of(buy));

    Transaction sell=new Transaction(
            1L,
            TransactionType.SELL,
            "AAPL",
            new BigDecimal("5"),
            new BigDecimal("100"));

    assertThrows(InsufficientHoldingsException.class, () -> service.createTransaction(sell));
    }

}
