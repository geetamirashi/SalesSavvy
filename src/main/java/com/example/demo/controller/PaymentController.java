package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Order_items;
import com.example.demo.entity.User;
import com.example.demo.service.PaymentService;
import com.razorpay.RazorpayException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
  
	public PaymentService paymentservice;
	
	public PaymentController(PaymentService paymentservice) {
		this.paymentservice=paymentservice;
	}
	
	@PostMapping("/create")
	public ResponseEntity<String>createPaymentOrder(@RequestBody Map<String,Object>requestBody,HttpServletRequest request){
		try {
			User user = (User)request.getAttribute("authenticatedUser");
		 BigDecimal totalamount = new BigDecimal(requestBody.get("totalAmount").toString());
		 List<Map<String,Object>> cartItem = (List<Map<String,Object>>)requestBody.get("cartItems");
		 
		 List<Order_items> cartlist = new ArrayList<>();
	      
		 for(Map<String,Object> map :cartItem) {
	    	    Order_items orderitem = new Order_items();
	    	     orderitem.setProductid((Integer)map.get("productId"));
	    	     orderitem.setQuantity((Integer)map.get("quantity"));
	    	     BigDecimal pricePerUnit = new BigDecimal(map.get("price").toString());
	    	     orderitem.setPriceperunit(pricePerUnit);
	    	     orderitem.setTotalPrice(pricePerUnit.multiply(BigDecimal.valueOf((Integer)map.get("quantity"))));
	    	     cartlist.add(orderitem);
	      }
	    
	     String razorpayOrderId = paymentservice.createOrder(user.getUserid(), totalamount, cartlist);
	     return ResponseEntity.ok(razorpayOrderId);
		} catch (RazorpayException e) {
			 e.printStackTrace();
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Creating Razopay");
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data:" +  e.getMessage());
		}
	}
	
	@PostMapping("/verify")
	public ResponseEntity<String> verifyPayment(@RequestBody Map<String,Object> requestBody,HttpServletRequest request) {
		  try {
			  User user = (User) request.getAttribute("authenticatedUser");
			  int userId = user.getUserid();
			  String razorpayOrderId = (String)requestBody.get("razorapayOrderId");
			  String razorapayPaymentId = (String)requestBody.get("razorpayPaymentId");
			  String razorapaySignature = (String) requestBody.get("razorapaySignature");
			  
			  boolean isVerified = paymentservice.verifyPayment(razorpayOrderId, razorapayPaymentId, razorapaySignature, userId);
			  if(isVerified) {
				  return ResponseEntity.ok("Payment verified successfully");
			  } else {
				  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment verification failed");
			  } 
		        }   catch(Exception e) {
				  e.printStackTrace();
				  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Verification Payment:" +e.getMessage());
			  }
			 }
	
}
