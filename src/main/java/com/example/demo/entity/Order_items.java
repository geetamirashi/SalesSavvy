package com.example.demo.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="order_items")
public class Order_items {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="orderid",nullable=false)
	private Orders order;
	
	@Column(name="productid", nullable=false)
    private int productid;
	
	@Column(name="quantity",nullable = false)
	private int quantity;
	
	@Column(name="price_per_unit",nullable=false)
	private BigDecimal priceperunit;
	
	@Column(name="totalamount",nullable = false)
	private BigDecimal totalPrice;
	
	
	public Order_items() {
		
	}


	public Order_items(int id, Orders order, int productid, int quantity, BigDecimal priceperunit,
			BigDecimal totalPrice) {
		super();
		this.id = id;
		this.order = order;
		this.productid = productid;
		this.quantity = quantity;
		this.priceperunit = priceperunit;
		this.totalPrice = totalPrice;
	}
	
	public Order_items(Orders order, int productid, int quantity, BigDecimal priceperunit,
			BigDecimal totalPrice) {
		super();
		this.order = order;
		this.productid = productid;
		this.quantity = quantity;
		this.priceperunit = priceperunit;
		this.totalPrice = totalPrice;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Orders getOrder() {
		return order;
	}


	public void setOrder(Orders order) {
		this.order = order;
	}


	public int getProductid() {
		return productid;
	}


	public void setProductid(int productid) {
		this.productid = productid;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public BigDecimal getPriceperunit() {
		return priceperunit;
	}


	public void setPriceperunit(BigDecimal priceperunit) {
		this.priceperunit = priceperunit;
	}


	public BigDecimal getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	
	

}
