package com.example.recetas.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getJwtSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String getJwtSecret() {
        return jwtSecret;
        
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
        
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getJwtSecret()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
