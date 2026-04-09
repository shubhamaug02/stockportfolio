package com.portfolio.stockportfolio.service;

import com.portfolio.stockportfolio.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    public String generateToken(User user) {
        return Jwts.builder().subject(user.getUsername()).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+jwtExpiration)).signWith(getSigningKey(secretKey)).compact();
    }

    public String extractName(String token) {
        return parser().verifyWith(getSigningKey(secretKey)).build().parseSignedClaims(token).getPayload();

    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
            return true;
    }

    private SecretKey getSigningKey(String secretKey){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

}
