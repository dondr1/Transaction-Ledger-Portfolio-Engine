package com.dhenamuthan.ledger.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhenamuthan.ledger.dto.PortfolioResponse;
import com.dhenamuthan.ledger.model.Transaction;
import com.dhenamuthan.ledger.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService; // for constructor injection
    public TransactionController ( TransactionService transactionService) {
        this.transactionService= transactionService;
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction);
    }

    @GetMapping("/{userId}")
    public List<Transaction> getUserTransactions(@PathVariable Long userId) {
        return transactionService.getUserTransactions(userId);
    }

    @GetMapping("/portfolio/{userId}")
    public PortfolioResponse getPortfolio(@PathVariable Long userId){
        return transactionService.getPortfolio(userId);
    }

}
//NOTES
// @RestController mrks this class as handling HTTP requests and returns JSON automatically
// @RequestMapping("/transactions") make the base URL as /transactions
// @PostMapping handles POST /transactions
// @RequestBody --- very IMPORTANT in the sense it tells -> take the JSON from the request body and convert it into a Transaction object
// @GetMapping handles GET /transactions
// @PathVariable takes the  the number after transactions/ an put it into user id 
//                  for eg. ( GET /transactions/1 it takes 1 and puts it as userId passing it as an argument)

