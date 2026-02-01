package com.example.demo.service;


import java.util.Map;

import com.example.demo.entity.User;


public interface CartItemService {
  public void addToCart(User user, int product, int quantity);
  public Map<String, Object> getCartItems(User authenticatedUser);
  public void updateCartItemQuantity(User authenticatedUser, int productId,int quantity);
  public void deleteCartItem(User authenticatedUser, int productId);
  public int getCartItemCount(int userid);
  
}
