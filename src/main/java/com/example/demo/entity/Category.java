package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="categories")
public class Category {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	private int categoryid;
	
	@Column(nullable = false, unique=true)
	private String categoryname ;
	
	
	public Category(){
		
	}
	
	public Category(int categoryid, String categoryname) {
		this.categoryid=categoryid;
		this.categoryname=categoryname;
	}
	
	public Category(String categoryname) {
		
		this.categoryname=categoryname;
	}
	
	

	public int getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	
	

}
