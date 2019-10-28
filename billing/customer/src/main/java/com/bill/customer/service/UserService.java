package com.bill.customer.service;

import com.bill.customer.config.ApplicationMessageSource;
import com.bill.customer.config.security.AppConfig;
import com.bill.customer.dao.repository.UserAuthenticationCodeRepository;
import com.bill.customer.dao.repository.UserRepository;
import com.bill.customer.dto.EmailMessage;
import com.bill.customer.dto.LoginRequestDTO;
import com.bill.customer.dto.UserRegisterRequest;
import com.bill.customer.entity.User;
import com.bill.customer.entity.UserAuthenticationCode;
import com.bill.customer.exception.AuthenticationException;
import com.bill.customer.exception.CustomerPortalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    UserAuthenticationCodeRepository userAuthenticationCodeRepository;
    @Autowired
    ApplicationMessageSource messageSource;
    @Autowired
    AppConfig appConfig;


    @Transactional
    public void registerUser(UserRegisterRequest registerRequest){

        if(userRepository.findByUsername(registerRequest.getUsername())!=null){
            throw new CustomerPortalException("User allready registered with given email id");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        String authenticationCode = UUID.randomUUID().toString();
        user.setToken(authenticationCode);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setName(registerRequest.getName());
        userRepository.save(user);

        String activationUrl = appConfig.getActivationBaseUrl()+"/"+authenticationCode;

        String activationMessage = messageSource.getMessage("account.activation.email.message", new Object[]{activationUrl});

        EmailMessage message = new EmailMessage(user.getUsername(), "Account activation request", activationMessage);
        emailService.send(message);
    }

    @Transactional
    public void login(LoginRequestDTO loginRequest){

        User user = userRepository.findByUsername(loginRequest.getUsername());

        if( user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new AuthenticationException("Invalid username or password");
        }

        if(!user.isActive()){
            throw new AuthenticationException("your account is not activated. Please activate your account");
        }

        // generate authenticated code and send on email/sms123
        UserAuthenticationCode userAuthenticationCode  =
                userAuthenticationCodeRepository.findByEmail(loginRequest.getUsername())
                        .orElse(new UserAuthenticationCode());
        //generating 4 digit authentication code for 2 factor authentication.
        String authenticationCode = ""+new Random().nextInt(10000);
        userAuthenticationCode.setCode(authenticationCode);
        userAuthenticationCode.setEmail(user.getUsername());
        userAuthenticationCodeRepository.save(userAuthenticationCode);
        String authenticationMessage = messageSource.getMessage("authentication.code.email.message", new Object[]{authenticationCode});
        EmailMessage message = new EmailMessage(user.getUsername(), "Login Request", ""+authenticationMessage);
        emailService.send(message);
    }
}
