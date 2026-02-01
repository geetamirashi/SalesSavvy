package com.example.demo.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.ProductImage;
import com.example.demo.entity.Products;
import com.example.demo.entity.User;
import com.example.demo.repository.CartRepositroy;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.ProductRepository;



@Service
public class CartImplementation implements  CartItemService {


	CartRepositroy cartrepository;
    ProductRepository productrepository;
	ImageRepository imageRepository;
	
	public CartImplementation(CartRepositroy cartRepositroy, ProductRepository productrepository,ImageRepository imageRepository) {
		this.cartrepository=cartRepositroy;
		this.productrepository=productrepository;
		this.imageRepository=imageRepository;
	}
	
	@Override
	public void addToCart(User user, int productid,int quantity) {
	    Products product = productrepository.findById(productid)
	    		.orElseThrow(()-> new IllegalArgumentException("Product not found with ID:" + productid));
	    Optional<CartItem> existingItem = cartrepository.findByUserAndProduct(user.getUserid(), productid);
	    if(existingItem.isPresent()) {
	    	   CartItem cartitem= existingItem.get();
	    	   cartitem.setQuantity(cartitem.getQuantity()+quantity);
	    	   cartrepository.save(cartitem); 
	    } else {
	    	     CartItem item = new CartItem(user, product, quantity);
	         cartrepository.save(item);
	    }
	   }

	@Override
	public Map<String, Object> getCartItems(User authenticatedUser) {
		List<CartItem> cartItems =
                cartrepository.findCartItemsWithProductDetails(
                        authenticatedUser.getUserid()
                );

        Map<String, Object> response = new HashMap<>();

        response.put("username", authenticatedUser.getUsername());
        response.put("role", authenticatedUser.getRole().name());

        List<Map<String, Object>> products = new ArrayList<>();

        int overallPrice = 0;
        
        for (CartItem cartItem : cartItems) {

            Products product = cartItem.getProduct();

            List<ProductImage> images =
                    imageRepository.findByProduct_Productid(
                            product.getProductid()
                    );

            String imageUrl =
                    (images != null && !images.isEmpty())
                            ? images.get(0).getImageurl()
                            : "default.png";

            Map<String, Object> item = new HashMap<>();

            item.put("productid", product.getProductid());
            item.put("imageurl", imageUrl);
            item.put("name", product.getName());
            item.put("description", product.getDescription());
            item.put("price_per_unit", product.getPrice());
            item.put("quantity", cartItem.getQuantity());
            double total =
                    cartItem.getQuantity() * product.getPrice().doubleValue();

            item.put("total_price", total);

            products.add(item);

            overallPrice += total;
        }

        Map<String, Object> cart = new HashMap<>();
        cart.put("products", products);
        cart.put("overall_total_price", overallPrice);

        response.put("cart", cart);

        return response;
	}

	@Override
	public void updateCartItemQuantity(User authenticatedUser, int productId, int quantity) {
		List<CartItem> cartItems =
                cartrepository.findCartItemsWithProductDetails(
                        authenticatedUser.getUserid()
                );
		Products product = productrepository.findById(productId)
		  .orElseThrow(()-> new IllegalArgumentException("Product not found "));
		  
		Optional<CartItem> existing = cartrepository.findByUserAndProduct(authenticatedUser.getUserid(), productId);
		if(existing.isPresent()) {
			CartItem cartitem = existing.get();
			if(quantity==0) {
				cartrepository.deleteCartItem(authenticatedUser.getUserid(), productId);
			} else {
				cartitem.setQuantity(quantity);
				cartrepository.save(cartitem);
			}
		}
	}
	

	@Override
	public void deleteCartItem(User authenticatedUser, int productId) {
		List<CartItem> cartItems =
                cartrepository.findCartItemsWithProductDetails(
                        authenticatedUser.getUserid()
                );
		Products product = productrepository.findById(productId)
				  .orElseThrow(()-> new IllegalArgumentException("Product not found "));
				  cartrepository.deleteCartItem(authenticatedUser.getUserid(), productId);
		
	}


	@Override
	public int getCartItemCount(int userid) {
		int count=cartrepository.countTotalItems(userid);
		return count;
	}
       
		
	}

	