package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.OrderStatus;
import com.example.demo.entity.Order_items;
import com.example.demo.entity.Orders;
import com.example.demo.repository.CartRepositroy;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.Order_itemRepository;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class PaymentServiceImplementation implements PaymentService {
   
	
   @Value("${razorpay.key_id}")
   private String razorapayKeyId;
   
   @Value("${razorpay.key_secret}")
   private String razorapayKeySecret;
   
   
   
   
   
   Order_itemRepository itemrepository;
   OrderRepository orderrepository;
   CartRepositroy cartrepository;
   
   
   public PaymentServiceImplementation( Order_itemRepository itemrepository,OrderRepository orderrepository,CartRepositroy cartrepository) {
	   this.itemrepository=itemrepository;
	   this.orderrepository= orderrepository;
	   this.cartrepository=cartrepository;
	   
   }
	
	@Override
	public String createOrder(int userid, BigDecimal totalAmount, List<Order_items> cartitems) throws RazorpayException {
		RazorpayClient client = new RazorpayClient(razorapayKeyId,razorapayKeySecret);
		var orderRequest = new JSONObject();
		orderRequest.put("amount", totalAmount.multiply(BigDecimal.valueOf(100)).intValue());
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", "txn_"+ System.currentTimeMillis());
		
		com.razorpay.Order razorpayOrder=client.orders.create(orderRequest);
		
		Orders order = new Orders();
		order.setOrderid(razorpayOrder.get("id"));
		order.setUserid(userid);
		order.setTotalamount(totalAmount);
		order.setStatus(OrderStatus.PENDING);
		order.setCreated_at(LocalDateTime.now());
		orderrepository.save(order);
		
		return razorpayOrder.get("id");
	}

	@Override
	public boolean verifyPayment(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature,
			int userid) {
		try {
			JSONObject attributes = new JSONObject();
			attributes.put("razorpay_order_id", razorpayOrderId);
			attributes.put("razorpay_payment_id", razorpayPaymentId);
			attributes.put("razorpay_signature", razorpaySignature);
			
			boolean isSignatureValid=com.razorpay.Utils.verifyPaymentSignature(attributes, razorapayKeySecret);
		        if(isSignatureValid) {
		        	     Orders  order = orderrepository.findById(razorpayOrderId).orElseThrow(()->new RuntimeException("Order not found"));
		        	     order.setStatus(OrderStatus.SUCCESS);
		        	     order.setUpdated_at(LocalDateTime.now());
		        	     orderrepository.save(order);
		        	     
		        	     List<CartItem> cartItems = cartrepository.findCartItemsWithProductDetails(userid);
		        	     for(CartItem item: cartItems) {
		        	     Order_items orderItem = new Order_items();
		        	     
		        	     orderItem.setProductid(item.getProduct().getProductid());
		        	     orderItem.setQuantity(item.getQuantity());
		        	     
		        	     orderItem.setPriceperunit(item.getProduct().getPrice());
		        	     orderItem.setTotalPrice(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
		        	     itemrepository.save(orderItem);
		        }
		             cartrepository.deleteAllCartItemsByUserId(userid);
		             return true;
	          } else {
	        	        return false;
	          }
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional
	public void saveOrderItems(String orderid, List<Order_items> items) {
		Orders order = orderrepository.findById(orderid).orElseThrow(()->new RuntimeException("Order not found"));
		for(Order_items item : items) {
			item.setOrder(order);
			itemrepository.save(item);
			
		}
		
	}

}
