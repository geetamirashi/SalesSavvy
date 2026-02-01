package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="orders")
public class Orders {
     
	@Id
	@Column(name="orderid")
	private String orderid;
	
	@Column(name="userid", nullable=false)
	private int userid;
	
	@Column(name="total_amount",nullable=false)
	private BigDecimal totalamount;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status", nullable = false)
	private OrderStatus status;
	
	@Column(name="created_at", nullable = false, updatable = false)
	private LocalDateTime created_at;
	
	@Column(name="updated_at")
	private LocalDateTime updated_at;
	
	@OneToMany(mappedBy = "order", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Order_items> orderitems;
	
	public Orders() {
		
	}

	public Orders(String orderid, int userid, BigDecimal totalamount, OrderStatus status, LocalDateTime created_at,
			LocalDateTime updated_at, List<Order_items> orderitems) {
		super();
		this.orderid = orderid;
		this.userid = userid;
		this.totalamount = totalamount;
		this.status = status;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.orderitems = orderitems;
	}
	
	public Orders(int userid, BigDecimal totalamount, OrderStatus status, LocalDateTime created_at,
			LocalDateTime updated_at, List<Order_items> orderitems) {
		super();
	
		this.userid = userid;
		this.totalamount = totalamount;
		this.status = status;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.orderitems = orderitems;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public BigDecimal getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(BigDecimal totalamount) {
		this.totalamount = totalamount;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public LocalDateTime getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}

	public List<Order_items> getOrderitems() {
		return orderitems;
	}

	public void setOrderitems(List<Order_items> orderitems) {
		this.orderitems = orderitems;
	}
	
	
	
	
	
	
	
}
