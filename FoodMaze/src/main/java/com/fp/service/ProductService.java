package com.fp.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fp.entity.Product;
import com.fp.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

	private ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public ResponseEntity<String> createProduct(@RequestBody Product product) {
		productRepository.save(product);
		return new ResponseEntity<String>("Product added successfully!", HttpStatus.OK);
	}
	
	@Transactional
	public ResponseEntity<String> deleteProductByCode(@RequestParam(name = "code") int code) {
		productRepository.deleteByProductCode(code);
		return new ResponseEntity<String>("Delete Entry with Id " + code, HttpStatus.OK);
	}

	public ResponseEntity<List<Product>> fetchAllProducts() {
		return new ResponseEntity<List<Product>>((List<Product>) productRepository.findAll(), HttpStatus.OK);
	}

	public ResponseEntity<Product> fetchProductByCode(@RequestParam(name = "code") int code) {
		return new ResponseEntity<Product>(productRepository.findByProductCode(code), HttpStatus.OK);
	}
	public ResponseEntity<List<Product>> fetchByCategory(@RequestParam(name = "category") String category) {
		return new ResponseEntity<List<Product>>(productRepository.findByCategory(category), HttpStatus.OK);

	}

}