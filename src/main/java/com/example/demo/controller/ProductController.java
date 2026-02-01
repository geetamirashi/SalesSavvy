package com.example.demo.controller;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Products;
import com.example.demo.entity.User;
import com.example.demo.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public ResponseEntity<Map<String, Object>> getProducts(
            @RequestParam(required = false) String category,
            HttpServletRequest request) {

        User authenticatedUser =
            (User) request.getAttribute("authenticatedUser");

        if (authenticatedUser == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Unauthorized"));
        }

        List<Products> products =
                productService.getProductByCategory(category);

        Map<String, Object> response = new HashMap<>();

        response.put("user", Map.of(
                "name", authenticatedUser.getUsername(),
                "role", authenticatedUser.getRole().name()
        ));

        List<Map<String, Object>> productList = new ArrayList<>();

        for (Products p : products) {
            Map<String, Object> map = new HashMap<>();
            map.put("product_id", p.getProductid());
            map.put("name", p.getName());
            map.put("description", p.getDescription());
            map.put("price", p.getPrice());
            map.put("stock", p.getStock());
            map.put("images",
                    productService.getProductImages(p.getProductid()));
            productList.add(map);
        }
                 response.put("products", productList);
                 return ResponseEntity.ok(response);
         } 

       }
    

