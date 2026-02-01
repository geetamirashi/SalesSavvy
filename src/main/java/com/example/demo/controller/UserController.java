package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.entity.UserDao;
import com.example.demo.service.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {


	private final UserService userService;
	   
	   
	   public UserController(UserService userService) {
		   this.userService = userService;
	   }
	   
	   @PostMapping("/register")
	   public ResponseEntity<?>registerUser(@RequestBody User user){
		   try {
			   User registeredUser = userService.registerUser(user);
			   return ResponseEntity.ok(Map.of("message",
					   "User register successfully", 
					   "user",
					   new UserDao(registeredUser.getUserid(), 
							   registeredUser.getUsername(),
							   registeredUser.getEmail(),
							   registeredUser.getRole().toString()
							   )));
		   }  catch(RuntimeException e) {
			     return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
		   }
	   }
	   @PostMapping("/forgot-password")
	   public ResponseEntity<?> forgotPassword(
	           @RequestBody Map<String, String> data) {

	       try {
	           userService.forgotPassword(
	                   data.get("email"),
	                   data.get("newPassword")
	           );

	           return ResponseEntity.ok(
	                   Map.of("message", "Password updated successfully")
	           );

	       } catch (RuntimeException e) {
	           return ResponseEntity.badRequest().body(
	                   Map.of("error", e.getMessage())
	           );
	       }
	   }

}
