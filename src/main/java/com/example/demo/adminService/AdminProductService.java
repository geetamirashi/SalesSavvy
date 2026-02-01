package com.example.demo.adminService;

import com.example.demo.entity.Products;

public interface AdminProductService {

	public Products addProductWithImage(String name, String description, Double price, Integer stock, Integer categoryId, String imageUrl);
	 public void deleteProduct(Integer productId) ;
	
}
