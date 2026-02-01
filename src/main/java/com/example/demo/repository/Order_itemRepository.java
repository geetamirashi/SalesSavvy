package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Order_items;



@Repository
public interface Order_itemRepository extends JpaRepository<Order_items, Integer> {
	@Query("SELECT oi FROM Order_items oi WHERE oi.order.orderid=:orderid")
	List<Order_items> findByOrderId(String orderid);

	@Query("SELECT oi FROM Order_items oi WHERE oi.order.userid=:userid AND oi.order.status='SUCCESS'")
	List<Order_items> findSuccessfulOrderItemsByUserId(int userid);
	
}
