package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  
	OrderService orderservice;
  
  public OrderController(OrderService orderservice) {
	  this.orderservice = orderservice;
  }
  
  @GetMapping
  public ResponseEntity<Map<String,Object>> getOrdersForUser(HttpServletRequest request){
	  try {
		  User authenticatedUser = (User)request.getAttribute("authenticatedUser");
		  
		  
		  Map<String,Object>response = orderservice.getOrdersForUser(authenticatedUser);
		  return ResponseEntity.ok(response);
		} catch(IllegalArgumentException e) {
			return ResponseEntity.status(400).body(Map.of("error",e.getMessage()));
		} catch(Exception e) {
			  e.printStackTrace();
			  return ResponseEntity.status(500).body(Map.of("error","An unexcepted error occured"));
		}
  }
  
}
