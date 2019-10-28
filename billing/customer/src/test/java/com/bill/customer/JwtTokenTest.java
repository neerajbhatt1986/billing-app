package com.bill.customer;

import com.bill.customer.config.security.AppConfig;
import com.bill.customer.entity.User;
import com.bill.customer.exception.AuthenticationException;
import com.bill.customer.service.JwtService;
import io.jsonwebtoken.Claims;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class JwtTokenTest {


    @InjectMocks
    JwtService jwtService;
    @Mock
    AppConfig appConfig;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();


    @Test
    public void generateTokenAndValidateData(){
        String name="Niraj Bhatt";
        String email = "neerajbhattneeraj@gmail.com";

        String token =generateToken(name, email);
        Assertions.assertThat(token).isNotNull();

        Claims claims = jwtService.validateJwtToken(token);
        Assertions.assertThat(claims.get("email")).isEqualTo(email);
        Assertions.assertThat(claims.get("name")).isEqualTo(name);
    }

    @Test
    public void checkExpiredToken() throws Exception{
        expectedEx.expect(AuthenticationException.class);
        expectedEx.expectMessage("Login session is expired");
        String name="Niraj Bhatt";
        String email = "neerajbhattneeraj@gmail.com";

        String token =generateToken(name, email);
        Assertions.assertThat(token).isNotNull();

        Thread.sleep(6000);
        jwtService.validateJwtToken(token);

    }

    @Test
    public void checkInvalidToken() throws Exception{
        expectedEx.expect(AuthenticationException.class);
        expectedEx.expectMessage("Invalid login token");
        String name="Niraj Bhatt";
        String email = "neerajbhattneeraj@gmail.com";

        String token =generateToken(name, email);
        Assertions.assertThat(token).isNotNull();
        jwtService.validateJwtToken(token+"x");

    }

    private String generateToken(String name, String email){
        Mockito.when(appConfig.getJwtSecret()).thenReturn("changeMe");
        Mockito.when(appConfig.getExpirationTime()).thenReturn(5000l);

        User user = new User();
        user.setName(name);
        user.setUsername(email);

        return jwtService.createJwtToken(user);
    }
}
