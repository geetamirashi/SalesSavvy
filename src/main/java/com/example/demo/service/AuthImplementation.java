package com.example.demo.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.SalesSavvyApplication;
import com.example.demo.entity.JWTTokens;
import com.example.demo.entity.User;
import com.example.demo.repository.JWTRepository;
import com.example.demo.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthImplementation implements AuthService {

    

    private final Key SIGNING_KEY;
    private final UserRepository userRepository;
    private final JWTRepository jwtTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthImplementation(
            UserRepository userRepository,
            JWTRepository jwtTokenRepository,
            @Value("${jwt.secret}") String jwtSecret, SalesSavvyApplication savvyApplication) {

        this.userRepository = userRepository;
        this.jwtTokenRepository = jwtTokenRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();

        
        if (jwtSecret.getBytes(StandardCharsets.UTF_8).length < 64) {
            throw new IllegalArgumentException(
                    "jwt.secret must be at least 64 characters long for HS512");
        }

        this.SIGNING_KEY = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

      
    }

    
    @Override
    public User authenticate(String username, String password) {

        User user = userRepository.findByUsername(username)
        		.orElseThrow(()-> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return user;
    }

    
    @Override
    public String generateToken(User user) {

        LocalDateTime now = LocalDateTime.now();
        JWTTokens existingToken =
                jwtTokenRepository.findByUserId(user.getUserid());

        // reuse token if not expired
        if (existingToken != null && now.isBefore(existingToken.getExpires_at())) {
            return existingToken.getToken();
        }

        // delete old token
        if (existingToken != null) {
            jwtTokenRepository.delete(existingToken);
        }

        String token = generateNewToken(user);
        saveToken(user, token);

        return token;
    }

    @Override
    public String generateNewToken(User user) {

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1 hour
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS512)
                .compact();
    }

   
    @Override
    public void saveToken(User user, String token) {

        JWTTokens jwtToken =
                new JWTTokens(user, token, LocalDateTime.now().plusHours(1));
             jwtTokenRepository.save(jwtToken);
    }


	@Override
	public boolean validateToken(String token) {
		try {
			System.out.println("VALIDATING TOKEN............");
			
			Jwts.parserBuilder()
			.setSigningKey(SIGNING_KEY)
			.build()
			.parseClaimsJws(token);
			Optional<JWTTokens> jwtToken = jwtTokenRepository.findByToken(token);
			if(jwtToken.isPresent()) {
				System.out.println("Token Expiry: " + jwtToken.get().getExpires_at());
				System.out.println("Current Time: " + LocalDateTime.now());
				return jwtToken.get().getExpires_at().isAfter(LocalDateTime.now());
			}
			return false;
		} catch(Exception e) {
			System.out.println("Token validation failed: " + e.getMessage());
			return false;
		}
	}
	

	@Override
	public String extractUsername(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(SIGNING_KEY)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}


	@Override
	public void logout(User user) {
		int userId=user.getUserid();
		JWTTokens token=jwtTokenRepository.findByUserId(userId);
	    
	}
}
