package com.app.url_shortener.security;

import com.app.url_shortener.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class JwtService {
    private final String SECRET = "My-key-My-key-My-key-My-key-My-key-"; //Later move to config
    private Key getSignKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }
    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",user.getRole().name());
        return Jwts.builder()
                .claims()
                    .add(claims)
                .and()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60)) //1 Hrs
                .signWith(getSignKey())
                .compact();
    }
    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String extractEmail(String token){
        return extractAllClaims(token).getSubject();
    }
    public boolean isTokenExpired(String token){
        return extractAllClaims(token).getExpiration().before(new Date());
    }
    public boolean isTokenValid(String token){
        try{
            extractAllClaims(token);
            return !isTokenExpired(token);
        }catch (Exception e){
            return false;
        }
    }
}

