package com.eccomrce.eccomrce.Config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {
    SecretKey key = Keys.hmacShaKeyFor(JwtContant.SECRET_KEY.getBytes());

    public String gerenateToken(Authentication auth) {
        String jwt = Jwts.builder().setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 846000000))
                .claim("email", auth.getName())
                .signWith(key).compact();

        return jwt;
    }

    public String gameEmailFromToken(String jwt) {
    try {
        System.out.println(jwt);
        // Check if the JWT has a proper prefix, e.g., "Bearer "
        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7); // Strip "Bearer " prefix
        }
        
       

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        String email = claims.get("email", String.class);
        return email;

    } catch (MalformedJwtException e) {
        
        throw new RuntimeException("Invalid token", e); // Or handle it as appropriate
    } catch (Exception e) {
        
        throw new RuntimeException("Could not extract email from token", e);
    }
}


}
