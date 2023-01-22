package br.com.fiap.spring.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.*;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expire}")
    private int expire;

    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        Date creationDate = getFromLocalDate(LocalDateTime.now());
        Date expiryDate =
                getFromLocalDate(LocalDateTime.now().plusMinutes(expire));
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(creationDate)
                .setExpiration(expiryDate)
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    private Date getFromLocalDate(LocalDateTime now) {
        return Date.from(now.toInstant(OffsetDateTime.now().getOffset()));
    }

    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
        return claims.getSubject();
    }
}
