package com.cfs.websocketservice.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    private static final String SECRET = "my-super-secure-secret-key-12345678901234567890:";

    public boolean validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
