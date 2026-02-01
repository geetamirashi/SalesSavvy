package com.example.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;



@Service
public class UserserviceImplementation implements UserService {
	
	private UserRepository userrepository;
	private BCryptPasswordEncoder passwordencode;
	
	public UserserviceImplementation(UserRepository userrepository) {
		this.userrepository=userrepository;
		this.passwordencode=new BCryptPasswordEncoder();
	}
	
	

	@Override
	public User registerUser(User user) {
		if (userrepository.findByUsername(user.getUsername()).isPresent()) {
		    throw new RuntimeException("Username already exists");
		}

		if (userrepository.findByEmail(user.getEmail()).isPresent()) {
		    throw new RuntimeException("Email already exists");
		}
		user.setPassword(passwordencode.encode(user.getPassword()));
		
		return userrepository.save(user);

	}



	@Override
	public void forgotPassword(String email, String newPassword) {
                   User user = userrepository.findByEmail(email)
	                .orElseThrow(() -> new RuntimeException("Email not registered"));

	        user.setPassword(passwordencode.encode(newPassword));
	        userrepository.save(user);
	    }
		
	
}
