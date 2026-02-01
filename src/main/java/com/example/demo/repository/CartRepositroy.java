package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CartItem;

import jakarta.transaction.Transactional;

@Repository
public interface CartRepositroy extends JpaRepository<CartItem, Integer> {

   
    @Query("SELECT c FROM CartItem c WHERE c.user.userid = :userid AND c.product.productid = :productid")
    Optional<CartItem> findByUserAndProduct(int userid, int productid);
    
   
    @Query("SELECT c FROM CartItem c JOIN FETCH c.product p WHERE c.user.userid = :userid")
    List<CartItem> findCartItemsWithProductDetails(int userid);

 
    @Query("UPDATE CartItem c SET c.quantity = :quantity WHERE c.id = :cartItemId")
    void updateCartItemQuantity(int cartItemId, int quantity);

    
    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem c WHERE c.user.userid = :userid AND c.product.productid = :productid")
    void deleteCartItem(int userid, int productid);

    
    @Query("SELECT COALESCE(SUM(c.quantity), 0) FROM CartItem c WHERE c.user.userid = :userid")
    int countTotalItems(int userid);
    
    
    @Query("DELETE FROM CartItem c WHERE c.user.userid = :userid")
    void deleteAllCartItemsByUserId(int userid);
}
