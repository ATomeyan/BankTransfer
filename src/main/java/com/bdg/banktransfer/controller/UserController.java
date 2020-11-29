package com.bdg.banktransfer.controller;

import com.bdg.banktransfer.entity.RoleName;
import com.bdg.banktransfer.entity.Transaction;
import com.bdg.banktransfer.entity.User;
import com.bdg.banktransfer.service.TransactionService;
import com.bdg.banktransfer.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    final UserService userService;
    final TransactionService transactionService;
    final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/get/{id}")
    public Optional<User> getById(@PathVariable int id) {
        return userService.getById(id);
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoleName(RoleName.ADMIN);

        return userService.save(user);
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<User> update(@RequestBody User user, @PathVariable int id, @RequestParam int adminId) {

        Optional<User> users = userService.getById(adminId);

        if (users.isPresent()) {

            Optional<User> optionalUser = userService.getById(id);

            if (users.get().getRoleName() == RoleName.ADMIN && optionalUser.isPresent()) {

                User user1 = optionalUser.get();
                user1.setRoleName(user1.getRoleName());
                return ResponseEntity.ok(userService.save(user));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        Optional<User> user = userService.getById(id);
        if (user.isPresent() && user.get().getRoleName() == RoleName.ADMIN) {
            userService.delete(id);
        }
    }

    @GetMapping("/{id}/transaction")
    public ResponseEntity<Transaction> getTransactionByUserId(@PathVariable int id) {

        Optional<User> users = userService.getById(id);

        if (users.isPresent()) {
            Transaction transaction = transactionService.getFilter(id);

            return ResponseEntity.ok(transaction);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/transaction/filter")
    public ResponseEntity<Transaction> getByDate(@PathVariable int id, @RequestParam String date) {

        Optional<User> users = userService.getById(id);
        LocalDate localDate = LocalDate.parse(date);

        if (users.isPresent()) {
            Transaction transaction = transactionService.getByDate(id, localDate);

            return ResponseEntity.ok(transaction);
        }

        return ResponseEntity.notFound().build();
    }
}