package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Products;



public interface ProductService {
  public List<Products> getProductByCategory(String  categoryName);
  public List<String> getProductImages(int productId);
}
