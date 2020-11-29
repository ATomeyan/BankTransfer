package com.bdg.banktransfer.service;

import com.bdg.banktransfer.entity.Transaction;
import com.bdg.banktransfer.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction getByDate(int id, LocalDate date) {
        return transactionRepository.getByDate(id, date);
    }

    public Transaction getFilter(int id) {
        return transactionRepository.getByFilter(id);
    }

    public Optional<Transaction> getById(int id) {
        return transactionRepository.findById(id);
    }

    public List<Transaction> getAll() {
        List<Transaction> transactionList = new ArrayList<>();
        Iterable<Transaction> transactions = transactionRepository.findAll();

        for (Transaction transaction : transactions) {
            transactionList.add(transaction);
        }

        return transactionList;
    }

    public Transaction save(Transaction transaction) {

        return transactionRepository.save(transaction);
    }

    public void delete(int id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);

        if (transaction.isPresent()) {
            transactionRepository.deleteById(id);
        }
    }
}