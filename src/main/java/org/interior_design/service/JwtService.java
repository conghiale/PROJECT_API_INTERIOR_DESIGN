package org.interior_design.service;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/10
 * Time: 4:49 PM
 */

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.interior_design.dto.TokenPair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @ 2025. All rights reserved
 */

@Service
@Log4j2
public class JwtService {

    @Value("${SECRET_KEY}")
    private String SECRET_KEY;
    @Value("${ACCESS_TOKEN_EXPIRATION}")
    private long ACCESS_TOKEN_EXPIRATION;
    @Value("${REFRESH_TOKEN_EXPIRATION}")
    private long REFRESH_TOKEN_EXPIRATION;
    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(genSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

//    Generate both tokens on login
    public TokenPair generateTokenPair(Map<String, Object> extraClaims, UserDetails userDetails) throws Exception {
        String accessToken = generateToken(extraClaims, userDetails.getUsername(), ACCESS_TOKEN_EXPIRATION);
        String refreshToken = generateToken(extraClaims, userDetails.getUsername(), REFRESH_TOKEN_EXPIRATION);
        return new TokenPair(accessToken, refreshToken, ACCESS_TOKEN_EXPIRATION, REFRESH_TOKEN_EXPIRATION);
    }


    public String generateToken(UserDetails userDetails, Long tokenExpiration) throws Exception {
        return generateToken(new HashMap<>(), userDetails.getUsername(), tokenExpiration);
    }

//    Generate Access Token
    public String generateToken(Map<String, Object> extraClaims, String username, Long tokenExpiration) throws Exception {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)) // millis seconds
                .signWith(genSignInKey(), ALGORITHM)
                .compact();
    }

    private Key genSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException e) {
//            e.printStackTrace(); // LOG server
            return true;
        } catch (Exception e) {
//            LOG.warn("[CHECK IS TOKEN EXPIRED] Token authentication error: {}", e.getMessage());
            return false;
        }
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(genSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.warn("[CHECK VALIDATE TOKEN] Token has expired!");
            throw e;
        } catch (UnsupportedJwtException e) {
            log.warn("[CHECK VALIDATE TOKEN] Token not supported!");
            throw e;
        } catch (MalformedJwtException e) {
            log.warn("[CHECK VALIDATE TOKEN] Invalid Token!");
            throw e;
        } catch (IllegalArgumentException e) {
            log.warn("[CHECK VALIDATE TOKEN] Token is empty or in the wrong format!");
            throw e;
        } catch (Exception e) {
            log.error("[CHECK VALIDATE TOKEN] Token ERROR: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            validateToken(token);
            final String username = extractUsername(token);

            return userDetails != null && userDetails.getUsername().equals(username);
        } catch (Exception e) {
            log.warn("[CHECK IS TOKEN VALID] Token authentication error: {}", e.getMessage());
            return false;
        }
    }
}
