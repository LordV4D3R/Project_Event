package com.antran.projectevent.util;

import com.antran.projectevent.constant.enums.AccountRole;
import com.antran.projectevent.model.Account;
import com.antran.projectevent.model.dto.TokenData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String extractFullName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public AccountRole extractRole(String token) {
        return AccountRole.valueOf(extractClaim(token, claims -> claims.get("role", String.class)));
    }

    public TokenData extractTokenData(String token) {
        TokenData tokenData = new TokenData();
        tokenData.setAccountRole(extractRole(token));
        tokenData.setUsername(extractClaim(token, Claims::getSubject));
        tokenData.setFullName(extractClaim(token, claims -> claims.get("fullName", String.class)));
        return tokenData;
    }


    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(TokenData tokenData, long expirationTime) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, tokenData, expirationTime);
    }

    private String createToken(Map<String, Object> claims, TokenData tokenData, long expirationTime) {
        claims.put("role", tokenData.getAccountRole());
        claims.put("fullName", tokenData.getFullName());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(tokenData.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token) {
        return (!isTokenExpired(token));
    }

}