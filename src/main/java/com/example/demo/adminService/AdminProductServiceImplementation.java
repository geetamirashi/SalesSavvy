package com.example.demo.adminService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.entity.ProductImage;
import com.example.demo.entity.Products;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.ProductRepository;



@Service
public class AdminProductServiceImplementation implements AdminProductService {

	private final ProductRepository productRepository;
    private final ImageRepository productImageRepository;
    private final CategoryRepository categoryRepository;

    public AdminProductServiceImplementation(ProductRepository productRepository, ImageRepository productImageRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.categoryRepository = categoryRepository;
    }

    public Products addProductWithImage(String name, String description, Double price, Integer stock, Integer categoryId, String imageUrl) {
        
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new IllegalArgumentException("Invalid category ID");
        }

        // Create and save the product
        Products product = new Products();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(BigDecimal.valueOf(price));
        product.setStock(stock);
        product.setCategory(category.get());
        product.setCreated_at(LocalDateTime.now());
        product.setUpdate_at(LocalDateTime.now());

        Products savedProduct = productRepository.save(product);

        // Create and save the product image
        if (imageUrl != null && !imageUrl.isEmpty()) {
            ProductImage productImage = new ProductImage();
            productImage.setProduct(savedProduct);
            productImage.setImageurl(imageUrl);
            productImageRepository.save(productImage);
        } else {
            throw new IllegalArgumentException("Product image URL cannot be empty");
        }

        return savedProduct;
    }

    public void deleteProduct(Integer productId) {
        // Check if the product exists
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("Product not found");
        }

        // Delete associated product images
        productImageRepository.deleteByProductId(productId);

        // Delete the product
        productRepository.deleteById(productId);
    }
}
	  

