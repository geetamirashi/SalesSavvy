package com.example.demo.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="product_images")
public class ProductImage {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int imageid;

   @ManyToOne()
   @JoinColumn(name="productid",nullable = false)
   private Products product;
	
   @Column(nullable = false)
   private String imageurl;
   
   
   public ProductImage() {
	   
   }
   
   


   public ProductImage(int imageid, Products product, String imageurl) {
	super();
	this.imageid = imageid;
	this.product = product;
	this.imageurl = imageurl;
   }
   
   public ProductImage(Products product, String imageurl) {
		super();
		
		this.product = product;
		this.imageurl = imageurl;
	}




   public int getImageid() {
	return imageid;
   }


   public void setImageid(int imageid) {
	this.imageid = imageid;
   }


   public Products getProduct() {
	return product;
   }


   public void setProduct(Products product) {
	this.product = product;
   }


   public String getImageurl() {
	return imageurl;
   }


   public void setImageurl(String imageurl) {
	this.imageurl = imageurl;
   }
   
   
}
