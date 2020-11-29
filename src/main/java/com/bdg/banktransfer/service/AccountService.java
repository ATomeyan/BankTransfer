package com.bdg.banktransfer.service;

import com.bdg.banktransfer.entity.Account;
import com.bdg.banktransfer.entity.User;
import com.bdg.banktransfer.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getByUser(int id, User user) {
        return accountRepository.getAccountByUser(id, user);
    }

    public Optional<Account> getById(int id) {
        return accountRepository.findById(id);
    }

    public List<Account> getAll() {
        List<Account> accountList = new ArrayList<>();
        Iterable<Account> accounts = accountRepository.findAll();

        for (Account account : accounts){
            accountList.add(account);
        }

        return accountList;
    }

    public Account save(Account account) {
        account = this.accountRepository.save(account);

        return account;
    }

    public void delete(int id) {
        Optional<Account> account = accountRepository.findById(id);

        if (account.isPresent()){
            accountRepository.deleteById(id);
        }
    }
}