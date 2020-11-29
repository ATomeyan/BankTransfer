package com.bdg.banktransfer.controller;

import com.bdg.banktransfer.entity.Account;
import com.bdg.banktransfer.entity.RoleName;
import com.bdg.banktransfer.entity.User;
import com.bdg.banktransfer.service.AccountService;
import com.bdg.banktransfer.service.UserService;
import javassist.NotFoundException;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;

    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @SneakyThrows
    @PostMapping("/create/{userId}")
    public Account createAccount(@PathVariable int userId, @RequestBody Account account) {

        Optional<User> users = userService.getById(userId);

        if (users.isPresent() && users.get().getRoleName() == RoleName.ADMIN) {

            return accountService.save(account);
        } else {
            throw new NotFoundException("User by " + userId + " not found");
        }
    }

    @SneakyThrows
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        User user = userService.logIn(email, password);

        if (user != null) {
            return ResponseEntity.ok(userService.getUser(email, password));
        } else {
            throw new NotFoundException("User by " + email + " not found");
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Account>> getUserAccounts(@PathVariable int id, @RequestBody User user) {

        Optional<User> users = userService.getById(id);
        if (users.isPresent()) {

            List<Account> accountList = accountService.getByUser(id, user);
            return ResponseEntity.ok(accountList);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getAll")
    public List<Account> getAll() {
        return accountService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        accountService.delete(id);
    }
}