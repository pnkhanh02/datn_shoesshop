//package com.example.shoesshop.utils;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import javax.servlet.http.HttpServletRequest;
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//
//@Component
//public class JWTUtility {
//    final long expirationMs = 24 * 60 * 60 * 1000; // One day
//    final String secret = "AGIUPVIEC38271980371248973242139847231047238473294712039487321948703";
//    final SecretKey secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());
//    private static JWTUtility instance;
//
//    // Private constructor to prevent instantiation from outside the class
//    private JWTUtility() {
//    }
//
//    // Method to get the singleton instance
//    public static JWTUtility getInstance() {
//        if (instance == null) {
//            synchronized (JWTUtility.class) {
//                if (instance == null) {
//                    instance = new JWTUtility();
//                }
//            }
//        }
//        return instance;
//    }
//
//    public String generateTokenWithEmail(String email) {
//
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + expirationMs);
//        return Jwts.builder()
//                .setSubject(email).setIssuedAt(now)
//                .setExpiration(expiryDate)
//                .signWith(secretKey)
//                .compact();
//    }
//
//
//    public Claims parseToken(String token) {
//
//        String tokenFix = token.replace("Bearer ", "");
//        // Generate the secret key for HS512
//        // Parse the JWT token
//        return Jwts.parserBuilder()
//                .setSigningKey(secretKey)
//                .build()
//                .parseClaimsJws(tokenFix)
//                .getBody();
//    }
//
//    public String extractTokenFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7); // Remove "Bearer " prefix
//        }
//        return null;
//    }
//
//    public String extractUsername(String token) {
//        return parseToken(token).getSubject();
//    }
//}
