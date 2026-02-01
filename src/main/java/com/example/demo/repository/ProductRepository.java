package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Products;



@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {
  List<Products> findByCategory_Categoryid(int categoryid);
  @Query("SELECT p.category.categoryname FROM Products p WHERE p.productid = :productId")
  String findCategoryNameByProductId(@Param("productId") int productId);
}
