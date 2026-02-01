package com.example.demo.filter;

import java.io.IOException;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = {"/api/*", "/admin/*"})
@Component
public class AuthenticationFilter implements Filter {

    private final AuthService authService;
    private final UserRepository userRepository;

    private static final String ALLOWED_ORIGIN = "http://localhost:5173";

    private static final String[] PUBLIC_PATHS = {
            "/api/auth/login",
            "/api/users/register",
            "/api/users/forgot-password"
    };

    public AuthenticationFilter(AuthService authService,
                                UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
        System.out.println("Authentication Filter Started");
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        res.setHeader("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            return;
        }

        String uri = req.getRequestURI();

        for (String path : PUBLIC_PATHS) {
            if (uri.startsWith(path)) {
                chain.doFilter(request, response);
                return;
            }
        }

        String token = getTokenFromCookie(req);

        if (token == null || !authService.validateToken(token)) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Unauthorized");
            return;
        }

        String username = authService.extractUsername(token);

        Optional<User> userOptional =
                userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        User user = userOptional.get();

     
        req.setAttribute("authenticatedUser", user);

        chain.doFilter(request, response);
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie c : request.getCookies()) {
            if ("authToken".equals(c.getName())) {
                return c.getValue();
            }
        }
        return null;
    }
}
