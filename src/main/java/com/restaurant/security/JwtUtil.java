package com.restaurant.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.restaurant.entities.User.Role;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private Set<String> blacklistedTokens = new HashSet<>();
    private static final SecretKey SECRET_KEY2 = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long EXPIRATION_TIME = 10 * 60 * 60 * 1000;

    @SuppressWarnings("deprecation")
	public String generateToken(String email , Role roles) {
        return Jwts.builder()
                .setSubject(email) 
                .claim("roles", roles) 
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) 
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) 
                .compact();
    }
    
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject); 
    }

    public String extractRole(String token) {
        @SuppressWarnings("deprecation")
		Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("roles", String.class);  
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);  
        return claimsResolver.apply(claims);  
    }

    @SuppressWarnings("deprecation")
	private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)  
                .parseClaimsJws(token)
                .getBody();  
    }


   
    public boolean validateToken(String token, String email) {
        final String extractedEmail = extractEmail(token);
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public void revokeToken(String token) {
        blacklistedTokens.add(token);
    }

    public boolean isTokenRevoked(String token) {
        return blacklistedTokens.contains(token);
    }

    public static String extractEmailFromToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            return extractEmailFromToken(token);
        } else {
            throw new RuntimeException("Token JWT non fourni ou mal formé.");
        }
    }

    @SuppressWarnings("deprecation")
	public static String extractEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY2)  
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();  
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'extraction du token JWT", e);
        }
    }
}
