package com.bill.customer.controller;

import com.bill.customer.dao.repository.UserRepository;
import com.bill.customer.dto.UserDTO;
import com.bill.customer.entity.User;
import com.bill.customer.exception.CustomerPortalException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;


    @GetMapping("user/details/{id}")
    UserDTO getUserDetails(@PathVariable Integer id){
        log.info("Getting user for id {} ", id);
        User user = userRepository.findById(id).orElseThrow(() -> new CustomerPortalException("No user find for the request"));
        return modelMapper.map(user, UserDTO.class);
    }
}
