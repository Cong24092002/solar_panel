package com.demo.MQTT.controller;

import com.demo.MQTT.Repository.AccountRepository;
import com.demo.MQTT.entity.Account;
import com.demo.MQTT.entity.AccountRequest;
import com.demo.MQTT.entity.Response;
import com.demo.MQTT.entity.Token;
import com.demo.MQTT.security.JwtService;
import com.demo.MQTT.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/solar/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;

    public AuthController(AuthenticationService authenticationService,
                          JwtService jwtService, AuthenticationManager authenticationManager, AccountRepository accountRepository) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.accountRepository = accountRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody AccountRequest accountDto){
        if(authenticationService.createAccount(accountDto)){
            String jwt = jwtService.generateToken(new Account(accountDto));
            return new ResponseEntity<>(new Response("Thành Công",new Token(jwt),"200","")
                    , HttpStatus.valueOf(200));
        }
        return new ResponseEntity<>(new Response("Thất bại",null,"400",
                "Username không hợp lệ hoặc đã tồn tại!"),
                HttpStatus.valueOf(200));
    }


    //note: fix lỗi sai pass --> 403 trắng trang
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AccountRequest accountRequest){
        Optional<Account> account = accountRepository.findByUsername(accountRequest.getUsername());
        if(account.isPresent()){
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    accountRequest.getUsername(), accountRequest.getPassword()
            ));
            String jwt = jwtService.generateToken(account.get());
            return new ResponseEntity<>(new Response("Thành công",new Token(jwt),"200",
                    ""),
                    HttpStatus.valueOf(200));
        }
        return new ResponseEntity<>(new Response("Thất bại",null,"400","Thông tin đăng nhập không hợp lệ"),
                HttpStatus.valueOf(200));

    }



}
