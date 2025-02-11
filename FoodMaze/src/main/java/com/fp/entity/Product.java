package com.fp.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int productCode;
	String productName;
	String productDesc;
	String category;
	double price;
	LocalDate mfgDate;
	LocalDate expDate;
	@Lob
	@Column(name = "image", columnDefinition = "LONGBLOB")
	private byte[] image;

	Product() {
	}

	public Product(int productCode, String productName, String productDesc, String category, double price,
			LocalDate mfgDate, LocalDate expDate, byte[] image) {
		super();
		this.productCode = productCode;
		this.productName = productName;
		this.productDesc = productDesc;
		this.category = category;
		this.price = price;
		this.mfgDate = mfgDate;
		this.expDate = expDate;
		this.image = image;
	}

	public int getProductCode() {
		return productCode;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getproductDesc() {
		return productDesc;
	}

	public void setproductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDate getMfgDate() {
		return mfgDate;
	}

	public void setMfgDate(LocalDate mfgDate) {
		this.mfgDate = mfgDate;
	}

	public LocalDate getExpDate() {
		return expDate;
	}

	public void setExpDate(LocalDate expDate) {
		this.expDate = expDate;
	}
}