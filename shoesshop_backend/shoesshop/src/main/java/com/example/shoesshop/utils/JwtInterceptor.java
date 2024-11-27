//package com.example.shoesshop.utils;
//
//import io.jsonwebtoken.Claims;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Date;
//
//@Component
//public class JwtInterceptor implements HandlerInterceptor {
//    private static final String AUTHORIZATION_HEADER = "Authorization";
//    private static final String TOKEN_PREFIX = "Bearer ";
//    private static volatile JwtInterceptor instance;
//    // Private constructor to prevent instantiation from outside the class
//    public JwtInterceptor() {
//    }
//    // Method to get the singleton instance
//    public static JwtInterceptor getInstance() {
//        if (instance == null) {
//            synchronized (JwtInterceptor.class) {
//                if (instance == null) {
//                    instance = new JwtInterceptor();
//                }
//            }
//        }
//        return instance;
//    }
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token = extractTokenFromRequest(request);
//        if (token == null || token.isEmpty() || !isValidToken(token)) {
//            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Missing or invalid token");
//            return false;
//        }
//        try {
//            Claims claims =  JWTUtility.getInstance().parseToken(token);
//            // You can perform additional validation or processing with the claims here
//            // Add the claims to the request attributes to make them accessible to other components
//            request.setAttribute("claims", claims);
//            return  true;
//        } catch (Exception e) {
//            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token");
//            return false;
//        }
//
//    }
//
//    public String extractTokenFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
//        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
//            return bearerToken.substring(TOKEN_PREFIX.length());
//        }
//        return null;
//    }
//
//    public  String extractUsername(String token) {
//        try {
//            Claims claims =  JWTUtility.getInstance().parseToken(token);
//            return  claims.getSubject();
//        } catch (Exception e) {
//            return null; // Token is invalid
//        }
//    }
//
//    public  boolean isValidToken(String token) {
//        try {
//            Claims claims =  JWTUtility.getInstance().parseToken(token);
//            Date expirationDate = claims.getExpiration();
//            String email = claims.getSubject();
//            Date currentDate = new Date();
//            return (expirationDate == null || !expirationDate.before(currentDate)) &&
//                    JwtTokenStore.getInstance().isTokenPresent(email, token.replace("Bearer ", "")); // Token has expired
//            // Token is valid and not expired
//        } catch (Exception e) {
//            return false; // Token is invalid
//        }
//    }
//}
