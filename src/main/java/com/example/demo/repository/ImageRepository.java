package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ProductImage;



@Repository
public interface ImageRepository extends JpaRepository<ProductImage, Integer> {
	List<ProductImage> findByProduct_Productid(int productid);

	@Query("DELETE FROM ProductImage pi WHERE pi.product.productid = :productId")
	void deleteByProductId(int productId);
}
