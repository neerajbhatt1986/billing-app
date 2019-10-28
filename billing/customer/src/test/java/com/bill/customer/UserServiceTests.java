package com.bill.customer;

import com.bill.customer.config.ApplicationMessageSource;
import com.bill.customer.config.security.AppConfig;
import com.bill.customer.dao.repository.UserAuthenticationCodeRepository;
import com.bill.customer.dao.repository.UserRepository;
import com.bill.customer.dto.UserRegisterRequest;
import com.bill.customer.entity.User;
import com.bill.customer.exception.CustomerPortalException;
import com.bill.customer.service.EmailService;
import com.bill.customer.service.UserService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {UserService.class})
public class UserServiceTests {

    @Autowired
    UserService userService;
    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    UserRepository userRepository;
    @MockBean
    EmailService emailService;

    @MockBean
    AppConfig appConfig;
    @MockBean
    ApplicationMessageSource messageSource;

    @MockBean
    UserAuthenticationCodeRepository userAuthenticationCodeRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();



    @Test
    public void registerUser(){

        String email = "test@xyz.com";
        Mockito.when(userRepository.findByUsername(email)).thenReturn(null);
        Mockito.when(userRepository.save(any())).thenReturn(new User());
        Mockito.when(appConfig.getActivationBaseUrl()).thenReturn("http://localhost:xxxx/");
        Mockito.when(messageSource.getMessage(anyString(), any(Object[].class))).thenReturn("Please activate you account");

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setName("Neeraj");
        userRegisterRequest.setPassword("abc@123");
        userRegisterRequest.setPassword2("abc@123");
        userRegisterRequest.setUsername(email);
        userService.registerUser(userRegisterRequest);

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(email);
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
        Mockito.verify(emailService, Mockito.times(1)).send(any());

    }

    @Test
    public void userAllreadyRegisterd(){
        expectedException.expect(CustomerPortalException.class);
        expectedException.expectMessage("User allready registered with given email id");

        String email = "test@xyz.com";
        Mockito.when(userRepository.findByUsername(email)).thenReturn(new User());

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setName("Neeraj");
        userRegisterRequest.setPassword("abc@123");
        userRegisterRequest.setPassword2("abc@123");
        userRegisterRequest.setUsername(email);
        userService.registerUser(userRegisterRequest);

    }

}
