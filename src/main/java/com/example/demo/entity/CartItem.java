package com.example.demo.entity;

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
@Table(name="cartitems")
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="userid", nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="productid", nullable = false)
	private Products product;
	
	@Column
	private int quantity;
	
	public CartItem() {
		
	}

	public CartItem(int id, User user, Products product, int quantity) {
		super();
		this.id = id;
		this.user = user;
		this.product = product;
		this.quantity = quantity;
	}
	
	public CartItem(User user, Products product, int quantity) {
		super();
		this.user = user;
		this.product = product;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, product, quantity, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		return id == other.id && Objects.equals(product, other.product) && quantity == other.quantity
				&& Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "CartItem [id=" + id + ", user=" + user + ", product=" + product + ", quantity=" + quantity + "]";
	}
	
	
	
	
}
