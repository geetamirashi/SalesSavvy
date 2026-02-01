package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int userid;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime created_at = LocalDateTime.now();
	
	@Column(nullable = false)
	private LocalDateTime update_at = LocalDateTime.now(); 
	
	
	public User() {
		
	}


	public User(int userid, String username, String email, String password, Role role, LocalDateTime created_at,
			LocalDateTime update_at) {
		super();
		this.userid = userid;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.created_at = created_at;
		this.update_at = update_at;
	}
	
	public User(String username, String email, String password, Role role, LocalDateTime created_at,
			LocalDateTime update_at) {
		super();
		
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.created_at = created_at;
		this.update_at = update_at;
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


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public LocalDateTime getCreated_at() {
		return created_at;
	}


	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}


	public LocalDateTime getUpdate_at() {
		return update_at;
	}


	public void setUpdate_at(LocalDateTime update_at) {
		this.update_at = update_at;
	}


	@Override
	public int hashCode() {
		return Objects.hash(created_at, email, password, role, update_at, userid, username);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(created_at, other.created_at) && Objects.equals(email, other.email)
				&& Objects.equals(password, other.password) && role == other.role
				&& Objects.equals(update_at, other.update_at) && userid == other.userid
				&& Objects.equals(username, other.username);
	}


	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", role=" + role + ", created_at=" + created_at + ", update_at=" + update_at + "]";
	}
	
	
	

	
}
