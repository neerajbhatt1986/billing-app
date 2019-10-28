package com.bill.customer.controller;

import com.bill.customer.dao.repository.UserRepository;
import com.bill.customer.dto.UserRegisterRequest;
import com.bill.customer.entity.User;
import com.bill.customer.exception.CustomerPortalException;
import com.bill.customer.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RegistrationController {

    private static Logger log = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("user/register")
    Map<String, String> registerUser(@Valid @RequestBody UserRegisterRequest registerRequest, HttpServletRequest request){

        userService.registerUser(registerRequest);

        Map<String, String> result = new HashMap<>();
        result.put("message", "Your account successfully created. Please check you email.");
        log.info("User {} successfully registerd", registerRequest.getUsername());

        return result;
    }

    @GetMapping("activate/account/{activationToken}")
    Map<String, String> activateAccount(@PathVariable String activationToken){
        User user =  userRepository.findByToken(activationToken);

        if(user == null){
            log.info("Invalid Activation request");
            throw new CustomerPortalException("Invalid activation request.");
        }

        if(user.isActive()){
            log.info("Your account is allready activated");
            throw new CustomerPortalException("Your account is allready activated");
        }

        user.setActive(true);
        userRepository.save(user);
        Map<String, String> result = new HashMap<>();

        result.put("message", "Your account is successfully activated");
        return result;
    }




}
