package com.jbrigido.library.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.time.expiration}")
    private long expiration;

    public String generateToken(UserDetails user) {

        Date issued = new Date();
        Date expirationDate = new Date(issued.getTime() + expiration);

        return "Bearer " + Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secretKey)
        );
    }

    public Claims extractAllClaims(String token) {
        SecretKey key = getSignInKey();
        return Jwts.parser().
                verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public Date extractExpiration(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    public boolean isValidToken(String token, UserDetails user) {
        String username = extractUsername(token);
        return user.getUsername().equals(username) && !isExpiredToken(token);
    }

    public boolean isExpiredToken(String token) {
        Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }
}
