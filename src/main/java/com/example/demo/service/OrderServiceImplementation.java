package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Order_items;
import com.example.demo.entity.ProductImage;
import com.example.demo.entity.Products;
import com.example.demo.entity.User;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.Order_itemRepository;
import com.example.demo.repository.ProductRepository;



@Service
public class OrderServiceImplementation implements OrderService {

	private Order_itemRepository orderItemRepository;
	private ProductRepository productRepository;
	private ImageRepository imageRepository;
	
	
	public OrderServiceImplementation(Order_itemRepository orderItemRepository,ProductRepository productRepository,ImageRepository imageRepository) {
		this.orderItemRepository=orderItemRepository;
		this.productRepository=productRepository;
		this.imageRepository=imageRepository;
	}
	
	@Override
	public Map<String, Object> getOrdersForUser(User user) {

	    List<Order_items> orderItems =
	            orderItemRepository.findSuccessfulOrderItemsByUserId(user.getUserid());

	    Map<String, Object> response = new HashMap<>();
	    response.put("username", user.getUsername());
	    response.put("role", user.getRole());

	    List<Map<String, Object>> products = new ArrayList<>();

	    for (Order_items item : orderItems) {

	        Products product =
	                productRepository.findById(item.getProductid()).orElse(null);

	        if (product == null) continue;

	        List<ProductImage> images =
	                imageRepository.findByProduct_Productid(product.getProductid());

	        String imageUrl =
	                images.isEmpty() ? null : images.get(0).getImageurl();

	        Map<String, Object> productDetails = new HashMap<>();
	        productDetails.put("order_id", item.getOrder().getOrderid());
	        productDetails.put("quantity", item.getQuantity());
	        productDetails.put("total_price", item.getTotalPrice());
	        productDetails.put("image_url", imageUrl);
	        productDetails.put("product_id", product.getProductid());
	        productDetails.put("name", product.getName());
	        productDetails.put("description", product.getDescription());
	        productDetails.put("price_per_unit", item.getPriceperunit());

	        products.add(productDetails);
	    }

	    response.put("products", products); 
	    return response;
	}
}