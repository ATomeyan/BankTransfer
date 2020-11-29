package com.bdg.banktransfer.repository;

import com.bdg.banktransfer.entity.Account;
import com.bdg.banktransfer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> getAccountByUser(int id, User user);
}