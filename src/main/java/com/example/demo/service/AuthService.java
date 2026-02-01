package com.example.demo.service;

import com.example.demo.entity.User;

public interface AuthService {
	public User authenticate(String  username, String password);
	public String generateToken(User user);
	public String generateNewToken(User user);
	public void saveToken(User user, String token);
	public boolean validateToken(String token);
	public  String extractUsername(String username);
	public void logout(User user);


}
