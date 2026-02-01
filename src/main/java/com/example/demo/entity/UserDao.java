package com.example.demo.entity;



public class UserDao {
	
	  private int userid;
	  private String username;
	  private  String email;
	  private String role;
	  
	  public  UserDao() {
		  
	  }

	  public UserDao(int userid, String username, String email, String role) {
		super();
		this.userid = userid;
		this.username = username;
		this.email = email;
		this.role = role;
	  }

	  public int getUserid() {
		  return userid;
	  }

	  public void setUserid(int userid) {
		  this.userid = userid;
	  }

	  public String getUsername() {
		  return username;
	  }

	  public void setUsername(String username) {
		  this.username = username;
	  }

	  public String getEmail() {
		  return email;
	  }

	  public void setEmail(String email) {
		  this.email = email;
	  }

	  public String getRole() {
		  return role;
	  }

	  public void setRole(String role) {
		  this.role = role;
	  }
	  


}
