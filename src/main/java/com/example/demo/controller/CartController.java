package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.CartItemService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartItemService cartservice;

    public CartController(CartItemService cartservice) {
        this.cartservice = cartservice;
    }

   
    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(
            @RequestBody Map<String, Object> request,
            HttpServletRequest req) {

        User user =
                (User) req.getAttribute("authenticatedUser");

        int productId = (int) request.get("productId");

        int quantity =
                request.containsKey("quantity")
                        ? (int) request.get("quantity")
                        : 1;

        cartservice.addToCart(user, productId, quantity);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    
    @GetMapping("/items")
    public ResponseEntity<Map<String, Object>> getCartItems(
            HttpServletRequest request) {

        User user =
                (User) request.getAttribute("authenticatedUser");

        Map<String, Object> cart =
                cartservice.getCartItems(user);

        return ResponseEntity.ok(cart);
    }

   
    @PutMapping("/update")
    public ResponseEntity<Void> updateCartItemQuantity(
            @RequestBody Map<String, Object> request,
            HttpServletRequest httpRequest) {

        User user =
                (User) httpRequest.getAttribute("authenticatedUser");

        int productId = (int) request.get("productId");
        int quantity = (int) request.get("quantity");

        cartservice.updateCartItemQuantity(
                user, productId, quantity);

        return ResponseEntity.ok().build();
    }

    
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCartItem(
            @RequestBody Map<String, Object> request,
            HttpServletRequest httpRequest) {

        User user = (User) httpRequest.getAttribute("authenticatedUser");

        int productId = (int) request.get("productId");

        cartservice.deleteCartItem(user, productId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/items/count")
    public ResponseEntity<Integer> getCartItemCount(
            HttpServletRequest request) {

        User user = (User) request.getAttribute("authenticatedUser");

        int count =
                cartservice.getCartItemCount(user.getUserid());

        return ResponseEntity.ok(count);
    }
}

