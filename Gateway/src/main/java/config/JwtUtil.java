package config;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;


@Component
public class JwtUtil {
    private final String secret = "secret";
    public boolean validateToken(String token) {
        try{
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
