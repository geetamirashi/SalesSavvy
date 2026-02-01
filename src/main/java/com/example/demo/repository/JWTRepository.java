package com.example.demo.repository;

import java.util.Optional;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.JWTTokens;

import jakarta.transaction.Transactional;
@Repository
public interface JWTRepository extends JpaRepository<JWTTokens, Integer>{
	
	
	@Query("SELECT t FROM JWTTokens t WHERE t.user.userid=:userId")
	public JWTTokens findByUserId(@Param("userId") int userId);
	Optional <JWTTokens> findByToken(String token);
	
	
	@Modifying
	@Transactional
	@Query("DELETE FROM JWTTokens t WHERE t.user.userid=:userdId")
	void deleteByUserId(@Param("userId") int userId);
	

}
