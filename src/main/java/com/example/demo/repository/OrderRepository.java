package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Orders;



@Repository
public interface OrderRepository extends JpaRepository<Orders, String> {
	
	@Query("SELECT o FROM Orders o WHERE MONTH(o.created_at) = :month AND YEAR(o.created_at) = :year AND o.status = 'SUCCESS'")
    List<Orders> findSuccessfulOrdersByMonthAndYear(int month, int year);
	
	 @Query("SELECT o FROM Orders o WHERE DATE(o.created_at) = :date AND o.status = 'SUCCESS'")
	    List<Orders> findSuccessfulOrdersByDate(LocalDate date);
	 
	 @Query("SELECT o FROM Orders o WHERE YEAR(o.created_at) = :year AND o.status = 'SUCCESS'")
	    List<Orders> findSuccessfulOrdersByYear(int year);

	 @Query("SELECT o FROM Orders o WHERE o.status = :status")
	    List<Orders> findAllByStatus(String status);
	 
	 @Query("SELECT o FROM Orders o WHERE o.status = 'SUCCESS'")
	    List<Orders> findAllByStatusForOverallBusiness();

	 
 


}
