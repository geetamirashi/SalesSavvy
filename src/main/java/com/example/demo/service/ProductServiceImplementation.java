package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.entity.ProductImage;
import com.example.demo.entity.Products;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.ProductRepository;



@Service
public class ProductServiceImplementation implements ProductService{

	
	private ProductRepository product;
	private ImageRepository image;
	private CategoryRepository categoryRepository;
		
	public ProductServiceImplementation(ProductRepository product, ImageRepository image,
			CategoryRepository categoryRepository) {
		super();
		this.product = product;
		this.image = image;
		this.categoryRepository = categoryRepository;
	}
	public List<Products> getProductByCategory(String categoryname) {
		if(categoryname!=null && !categoryname.isEmpty()) {
			Optional<Category> categoryOpt = categoryRepository.findByCategoryname(categoryname);
			if(categoryOpt.isPresent()) {
				Category category = categoryOpt.get();
				return product.findByCategory_Categoryid(category.getCategoryid());
			} else {
				throw new RuntimeException("Category not found");
			}
		} else {
			  return product.findAll();
		}
	}
	@Override
	public List<String> getProductImages(int productId) {
		  List<ProductImage> productImages = image.findByProduct_Productid(productId);
		  List<String> imageUrls = new ArrayList<>();
		  for(ProductImage image : productImages) {
			  imageUrls.add(image.getImageurl());
		  }
		   return imageUrls;
		
	}
	

}
