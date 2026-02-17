package com.dhenamuthan.ledger.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dhenamuthan.ledger.dto.PortfolioResponse;
import com.dhenamuthan.ledger.exception.InsufficientFundsException;
import com.dhenamuthan.ledger.exception.InsufficientHoldingsException;
import com.dhenamuthan.ledger.model.Transaction;
import com.dhenamuthan.ledger.model.TransactionType;
import com.dhenamuthan.ledger.repository.TransactionRepository;


@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    // we used 'private' here for encapsulation ONLY transactionService should use this repository 
    //and we used 'final' once assigned to the constructor it can never be REASSIGNED
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    public Transaction saveTransaction ( Transaction transaction ) {
        return transactionRepository.save(transaction);
        }
    public List<Transaction> getUserTransactions(Long userId) {
        return transactionRepository.findByUserId(userId);
    }
    public BigDecimal calculateAssetHoldings(Long userId, String asset) {
        List<Transaction> userTassets= transactionRepository.findByUserIdAndAsset(userId, asset);
        BigDecimal total = BigDecimal.ZERO;
        for (int idx = 0; idx < userTassets.size(); idx++) {
            Transaction elem = userTassets.get(idx);
            if (elem.getType()== TransactionType.BUY){
                total=total.add(elem.getQuantity());
            }else if ( elem.getType()==TransactionType.SELL){
                total=total.subtract(elem.getQuantity());
            }
        };
        return total;

    }
    public BigDecimal calculateCashBalance(Long userId) {
        List<Transaction> userTransactions = transactionRepository.findByUserId(userId);
        BigDecimal balance = BigDecimal.ZERO ;
        for (int i=0; i< userTransactions.size(); i++) {
            Transaction t = userTransactions.get(i);
            if (null==t.getType()) {
                balance=balance.add(t.getQuantity());
            }else switch (t.getType()) {
                case BUY -> {
                    BigDecimal temp= t.getQuantity();
                    temp=temp.multiply(t.getPrice());
                    balance=balance.subtract(temp);
                }
                case SELL -> {
                    BigDecimal t2= t.getQuantity();
                    t2=t2.multiply(t.getPrice());
                    balance=balance.add(t2);
                }
                default -> balance=balance.add(t.getQuantity());
            }
        }
        return balance;

    }
    //NOTE: BigDecimal is an object not a primitev that you can direty compare with > or < you have to use .compareTo(something) instead

    public Transaction createTransaction(Transaction transaction) {
    //SELL validation
    if (transaction.getType() == TransactionType.SELL) {
        BigDecimal holdings = calculateAssetHoldings(
        transaction.getUserId(),
        transaction.getAsset()
        );
        if (holdings.compareTo(transaction.getQuantity())<0) {
            throw new InsufficientHoldingsException("Insufficient asset holdings");

        }
    }

    //BUY validation
    if (transaction.getType() == TransactionType.BUY) {
        BigDecimal cashBalance= calculateCashBalance(transaction.getUserId());
        BigDecimal cost= transaction.getQuantity().multiply(transaction.getPrice());
        if (cashBalance.compareTo(cost)< 0) {
            throw new InsufficientFundsException("Insufficient cash balance");

        }
    }
    return transactionRepository.save(transaction);
    }

    public PortfolioResponse getPortfolio(Long userId) {
        List<Transaction> transactions=transactionRepository.findByUserId(userId);
        Map<String, BigDecimal> holdings = new HashMap<>();
        for (Transaction t : transactions) {
            if (t.getType()==TransactionType.BUY) {
                BigDecimal current = holdings.getOrDefault(
                    t.getAsset(),
                    BigDecimal.ZERO
                );
                BigDecimal updated = current.add(t.getQuantity());
                holdings.put(t.getAsset(), updated);
            } else if (t.getType() == TransactionType.SELL) {
                BigDecimal current = holdings.getOrDefault(
                    t.getAsset(),
                    BigDecimal.ZERO
                );
                BigDecimal updated = current.subtract(t.getQuantity());
                holdings.put(t.getAsset(), updated);
            }
        }
        
        BigDecimal cashBalance = calculateCashBalance(userId);
        holdings.entrySet().removeIf(entry -> entry.getValue().compareTo(BigDecimal.ZERO)==0); //iterates through the map and removes any entry where value is 0
        return new PortfolioResponse(cashBalance, holdings);
    }

}

