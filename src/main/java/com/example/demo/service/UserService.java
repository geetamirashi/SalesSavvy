package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
	public User registerUser(User user);
	public void forgotPassword(String email, String newPassword);

}
