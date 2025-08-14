package com.bookBazaar.services.implementations;

import com.bookBazaar.models.other.AccessToken;
import com.bookBazaar.services.interfaces.AccessTokenServiceINT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.io.Decoders;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccessTokenServiceIMPL implements AccessTokenServiceINT {

    private final Key secretKey;
    public AccessTokenServiceIMPL(@Value("${jwt.secret}") String secretKey){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }
    @Override
    public String encode(AccessToken accessToken) {
        Map<String,Object> claimsMap = new HashMap<>();
        if(!accessToken.getRole().isBlank()) {
            claimsMap.put("role", "ROLE_"+accessToken.getRole());
        }
        if(accessToken.getId() != null) {
            claimsMap.put("userId", accessToken.getId());
        }

        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(accessToken.getSubject())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(3, ChronoUnit.HOURS)))
                .addClaims(claimsMap)
                .signWith(secretKey)
                .compact();
        }

    @Override
    public AccessToken decode(String encodedAccessToken) {
       try {
           Jwt jwt = Jwts.parserBuilder().setSigningKey(secretKey).build().parse(encodedAccessToken);
           Claims claims = (Claims) jwt.getBody();
           String role = claims.get("role", String.class);
           return AccessToken.builder()
                   .subject(claims.getSubject())
                   .id(claims.get("userID", Long.class))
                   .role(role)
                   .build();
       }
       catch (Exception e)
       {
           throw new SecurityException(e.getMessage());
       }
    }
}
