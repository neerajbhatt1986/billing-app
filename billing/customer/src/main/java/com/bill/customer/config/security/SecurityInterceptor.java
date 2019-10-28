package com.bill.customer.config.security;

import com.bill.customer.dao.repository.UserRepository;
import com.bill.customer.entity.User;
import com.bill.customer.exception.AuthenticationException;
import com.bill.customer.service.JwtService;
import com.bill.customer.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("auth-token");
        Claims claims = jwtService.validateJwtToken(token);
        User user = userRepository.findByUsername((String) claims.get("email"));
        if(user == null){
            throw new AuthenticationException("Invalid Authentication request");
        }
        if(!token.equals(user.getLoginToken())){
            throw new AuthenticationException("Your session has been closed");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
