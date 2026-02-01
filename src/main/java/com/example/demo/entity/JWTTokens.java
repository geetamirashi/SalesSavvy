package com.example.demo.entity;



import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="jwt_tokens")
public class JWTTokens {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int token_id;
	
	@ManyToOne
	@JoinColumn(name="userid", nullable=false)
	private User user;
	
	@Column(nullable=false)
	private String token;
	
	
	@Column(nullable=false)
	private LocalDateTime expires_at;
	
	public JWTTokens() {
		
	}

	public JWTTokens(int token_id, User user, String token, LocalDateTime expires_at) {
		super();
		this.token_id = token_id;
		this.user = user;
		this.token = token;
		this.expires_at = expires_at;
	}
	
	public JWTTokens(User user, String token, LocalDateTime expires_at) {
		super();
		this.user = user;
		this.token = token;
		this.expires_at = expires_at;
	}

	public int getToken_id() {
		return token_id;
	}

	public void setToken_id(int token_id) {
		this.token_id = token_id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getExpires_at() {
		return expires_at;
	}

	public void setExpires_at(LocalDateTime expires_at) {
		this.expires_at = expires_at;
	}

	@Override
	public int hashCode() {
		return Objects.hash(expires_at, token, token_id, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JWTTokens other = (JWTTokens) obj;
		return Objects.equals(expires_at, other.expires_at) && Objects.equals(token, other.token)
				&& token_id == other.token_id && Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "JWTTokens [token_id=" + token_id + ", user=" + user + ", token=" + token + ", expires_at=" + expires_at
				+ "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
