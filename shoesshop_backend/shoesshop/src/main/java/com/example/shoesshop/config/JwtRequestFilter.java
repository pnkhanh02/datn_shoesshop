package com.example.shoesshop.config;

import com.example.shoesshop.dto.LoginDTO;
import com.example.shoesshop.utils.JWTTokenUtils;
import com.sun.istack.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION = "Authorization";


    @Autowired
    private JWTTokenUtils jwtTokenUtils;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        // Lấy token từ api (request)
        String token = httpServletRequest.getHeader(AUTHORIZATION);
        //Lấy thông tin API hiện tại để kiểm tra API này có public hay không. Nếu được public -> không cần check token
        String request = httpServletRequest.getRequestURI();
        if (StringUtils.containsAnyIgnoreCase(request, "/api/v1/auth/")
                || StringUtils.containsAnyIgnoreCase(request, "/api/v1/payment/vn-pay-callback")
                || StringUtils.containsAnyIgnoreCase(request, "/api/v1/product/search")
                || StringUtils.containsAnyIgnoreCase(request, "/swagger-ui")
                || StringUtils.containsAnyIgnoreCase(request, "/swagger-resources")
                || StringUtils.containsAnyIgnoreCase(request, "/v3/api-docs")) {
            // Những api public ko cần check token -> doFilter
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            //Các trường hợp cần check token
            // Kiểm tra token & Giải mã token -> lấy thông tin user -> authen
            LoginDTO loginDto = jwtTokenUtils.checkToken(token, httpServletResponse, httpServletRequest);
            if (loginDto != null) {
                // Lấy danh sách quyền của user
                List<GrantedAuthority> authorities = new ArrayList<>();
                // Chuyển từ Account.Role sang SimpleGrantedAuthority
                authorities.add(new SimpleGrantedAuthority(loginDto.getRole().name()));
                // Tạo đối tượng để Authen vào hệ thống
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(), null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication); // Xác thực được người dùng thành công
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        }
    }
}
