package com.PargmaBootcamp2024.ShoppingCartMicroservice.adapters.driven.jpa.mysql.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secret_key;


    private Key generateKey() {

        return Keys.hmacShaKeyFor(secret_key.getBytes());
    }

    public String extractUsername(String jwt) {

        return extractAllClaims(jwt).getSubject();

    }

    public String extractAuthorities(String jwt) {
        return extractAllClaims(jwt).get("authorities").toString();
    }



    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(jwt).getBody();
    }

}
