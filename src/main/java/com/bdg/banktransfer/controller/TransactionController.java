package com.bdg.banktransfer.controller;

import com.bdg.banktransfer.entity.*;
import com.bdg.banktransfer.service.AccountService;
import com.bdg.banktransfer.service.TransactionService;
import com.bdg.banktransfer.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("api/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final UserService userService;

    public TransactionController(TransactionService transactionService, AccountService accountService, UserService userService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Transaction transaction, @RequestParam int userId, @RequestParam int accountId) {

        Optional<Account> accounts = accountService.getById(accountId);
        Optional<User> users = userService.getById(userId);
        Account account;

        if (accounts.isPresent() && users.isPresent()) {
            account = accounts.get();

            account.setType(Type.Deposit);
            transaction.setStatus(Status.PENDING);
            transaction.setAccount(account);
            transaction.setLocalDate(LocalDate.now());
            return ResponseEntity.ok(transactionService.save(transaction));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/update{transactionId}")
    public Transaction update(@RequestBody Transaction transaction,
                              @PathVariable int transactionId,
                              @RequestParam int adminId) {

        Optional<User> user = userService.getById(adminId);
        Optional<Transaction> transactions = transactionService.getById(transactionId);

        if (user.isPresent() && transactions.isPresent()) {
            if (transactions.get().getStatus() == Status.PENDING) {

                transaction.setStatus(Status.ACCEPTED);
                transactionService.save(transaction);
            }
        }
        return transaction;
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<?> cancelTransaction(@PathVariable int id) {
        Optional<Transaction> transactions = transactionService.getById(id);
        Transaction transaction;
        if (transactions.isPresent() && transactions.get().getStatus() == Status.PENDING) {
            transaction = transactions.get();
            transaction.setStatus(Status.CANCEL);
            return ResponseEntity.ok(transactionService.save(transaction));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}