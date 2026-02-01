package com.example.demo.service;

import java.math.BigDecimal;
import java.util.List;

import com.example.demo.entity.Order_items;

public interface PaymentService {
	public String createOrder(int userid,BigDecimal totalamount, List<Order_items>cartitems) throws Exception;
    public boolean verifyPayment(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature, int userid);
}
