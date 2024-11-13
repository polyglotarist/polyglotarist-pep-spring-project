package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;



@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    //1: Our API should be able to process new User registrations.

    public Account registerUser(Account account){
        return accountRepository.save(account);
    }

    //helper method that gets account by username
    public Account findByUsername(String username){
        return accountRepository.findByUsername(username);
    }

    //helper method username exists return true if a username already exists
    public boolean existsByUsername(String username){
        return accountRepository.existsByUsername(username);
    }

    //helper method find user by id, 
    public Account findById(int id){
        return accountRepository.findById(id);
    }
    


    //2: Our API should be able to process User logins.
    public Account login(String username, String password){
        return accountRepository.findByUsernameAndPassword(username, password);
    }
}
