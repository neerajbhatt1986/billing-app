package com.bill.customer.service;

import com.bill.customer.config.security.AppConfig;
import com.bill.customer.entity.User;
import com.bill.customer.exception.AuthenticationException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {


    private static Logger log = LoggerFactory.getLogger(JwtService.class);

    @Autowired
    private AppConfig appConfig;

    public String createJwtToken(User user){
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(appConfig.getJwtSecret());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getUsername());
        claims.put("name", user.getName());
        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId("")
                .setIssuedAt(now)
                .setSubject("authentication")
                .setIssuer("biller")
                .setClaims(claims)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        long expMillis = nowMillis + appConfig.getExpirationTime();
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);

        return builder.compact();
    }

    public Claims validateJwtToken(String jwt){
        try{
            return Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(appConfig.getJwtSecret()))
                    .parseClaimsJws(jwt).getBody();

        } catch (SignatureException signatureException){
            throw new AuthenticationException("Invalid login token");
        } catch (ExpiredJwtException e){
            throw new AuthenticationException("Login session is expired");
        }
    }
}
