package com.bill.customer.controller;

import com.bill.customer.dao.repository.UserAuthenticationCodeRepository;
import com.bill.customer.dao.repository.UserRepository;
import com.bill.customer.dto.LoginRequestDTO;
import com.bill.customer.dto.request.MailAuthenticationRequest;
import com.bill.customer.entity.User;
import com.bill.customer.entity.UserAuthenticationCode;
import com.bill.customer.exception.AuthenticationException;
import com.bill.customer.exception.CustomerPortalException;
import com.bill.customer.service.JwtService;
import com.bill.customer.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class LoginController {

    Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserAuthenticationCodeRepository userAuthenticationCodeRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    private UserService userService;

    @PostMapping("login")
    Object login(@Valid @RequestBody LoginRequestDTO loginRequest){
        userService.login(loginRequest);
        Map<String, String> result = new HashMap<>();

        result.put("message", "Please check you email and enter authentication code");
        return result;
    }


    /*2 factor authentication for Man in middle attack*/
    @PostMapping("mail/authentication")
    Map<String, String> mailAuthentication(@Valid @RequestBody MailAuthenticationRequest mailAuthenticationRequest){

        log.info("Authenication user {} by authentication code ", mailAuthenticationRequest.getEmail());
        Map<String, String> result = new HashMap<>();
        UserAuthenticationCode authenticationCode =userAuthenticationCodeRepository.findByEmailAndCode(mailAuthenticationRequest.getEmail(), mailAuthenticationRequest.getAuthenticationCode());
        if(authenticationCode == null){
            log.info("Please provide valid authenticate code");
            throw new AuthenticationException("Please provide valid authentication code");
        }
        // deleting authentication code once jwt token is generated.
        userAuthenticationCodeRepository.delete(authenticationCode);
        //generate a JWT token and send to the in response
        log.info("User {} successfully login", authenticationCode.getEmail());
        String loginToken = jwtService.createJwtToken(userRepository.findByUsername(authenticationCode.getEmail()));
        User user= userRepository.findByUsername(mailAuthenticationRequest.getEmail());
        if(user == null){
            throw new AuthenticationException("No user exist with given email id");
        }
        user.setLoginToken(loginToken);
        userRepository.save(user);

        result.put("token", loginToken);
        return result;
    }




}
