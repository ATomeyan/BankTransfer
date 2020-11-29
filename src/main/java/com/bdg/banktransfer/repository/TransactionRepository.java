package com.bdg.banktransfer.repository;

import com.bdg.banktransfer.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Transaction getByDate(int id, LocalDate date);
    Transaction getByFilter(int id);
}