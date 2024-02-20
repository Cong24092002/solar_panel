package com.demo.MQTT.service;

import com.demo.MQTT.Repository.AccountRepository;
import com.demo.MQTT.Repository.UserRepository;
import com.demo.MQTT.entity.Account;
import com.demo.MQTT.entity.AccountRequest;
import com.demo.MQTT.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthenticationService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public List<Account> getListAccount() {
        return accountRepository.findAll();
    }
    public Boolean isUserNameValid(String username) {
        if (username.equals("")) return false;
        List<Account> list = getListAccount();
        for (Account acc : list) {
            if (acc.getUsername().equals(username)) return false;
        }
        return true;
    }
    public boolean createAccount(AccountRequest accountRequest){
        if (isUserNameValid(accountRequest.getUsername())) {
            Account newAccount = new Account(accountRequest);
            newAccount.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
            User user = new User();
            userRepository.save(user);
            newAccount.setUserId(user.getId());
            accountRepository.save(newAccount);

            return true;
        }
        return false;
    }
}
