package com.cfs.authservice.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;



@Component
public class JwtUtility {


    public static final String SECRET = "my-super-secure-secret-key-12345678901234567890:";
    public String generateToken(String username) {
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

        return Jwts.builder()
                .setSubject(username)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
