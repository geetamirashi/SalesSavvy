package com.example.demo.adminService;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.JWTRepository;
import com.example.demo.repository.UserRepository;



@Service
public class AdminUserServiceImplementation implements AdminUserService{
   
	 private final UserRepository userRepository;
	    private final JWTRepository jwtRepository;

	    public AdminUserServiceImplementation(UserRepository userRepository, JWTRepository jwtRepository) {
	        this.userRepository = userRepository;
	        this.jwtRepository = jwtRepository;
	    }

	    @Transactional
	    public User modifyUser(Integer userId, String username, String email, String role) {
	        // Check if the user exists
	        Optional<User> userOptional = userRepository.findById(userId);
	        if (userOptional.isEmpty()) {
	            throw new IllegalArgumentException("User not found");
	        }
	        User existingUser = userOptional.get();

	        // Update user fields
	        if (username != null && !username.isEmpty()) {
	            existingUser.setUsername(username);
	        }
	        if (email != null && !email.isEmpty()) {
	            existingUser.setEmail(email);
	        }
	        if (role != null && !role.isEmpty()) {
	            try {
	                existingUser.setRole(Role.valueOf(role));
	            } catch (IllegalArgumentException e) {
	                throw new IllegalArgumentException("Invalid role: " + role);
	            }
	        }

	        // Delete associated JWT tokens
	        jwtRepository.deleteById(userId);

	        // Save updated user
	        return userRepository.save(existingUser);
	    }

	    public User getUserById(Integer userId) {
	        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
	    }

	
	
}
