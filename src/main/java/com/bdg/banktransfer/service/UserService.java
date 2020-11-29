package com.bdg.banktransfer.service;

import com.bdg.banktransfer.entity.User;
import com.bdg.banktransfer.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        Iterable<User> users = userRepository.findAll();

        for (User user : users) {
            userList.add(user);
        }
        return userList;
    }

    public Optional<User> getById(int id) {
        return userRepository.findById(id);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User save(User user) {

        if (user.getEmail().isEmpty() && user.getPassword().isEmpty()){
            throw new NoSuchElementException();
        }

        user = userRepository.save(user);

        return user;
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }

    public User logIn(String email, String password) {
        return userRepository.signIn(email, password);
    }

    public User getUser(String email, String password) {
        return userRepository.getByEmailAndPassword(email, password);
    }
}