package com.fp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fp.entity.Product;
import com.fp.service.ProductService;

@RestController
@RequestMapping(path = "/api/product")
public class ProductController {

	public ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping
	public ResponseEntity<String> createProduct(@RequestBody Product product) {
		return productService.createProduct(product);
	}

	@DeleteMapping(value = "/code")
	public ResponseEntity<String> deleteProductByCode(@RequestHeader(value = "code") int code) {
		return productService.deleteProductByCode(code);
	}
  
	@GetMapping
	public ResponseEntity<List<Product>> fetchAllProducts() {
		return productService.fetchAllProducts();
	}


	@PutMapping(value = "/code/price")
	public ResponseEntity<String> updatePrice(@RequestHeader(value = "productPrice") double orderPrice,
			@RequestHeader(value = "code") int code) {
		return productService.updatePrice(orderPrice, code);
	}


	@GetMapping(value = "/price")
	public ResponseEntity<List<Product>> fetchByPriceRange(@RequestHeader(value = "lprice") double lprice,
			@RequestHeader(value = "uprice") double uprice) {
		return productService.fetchByPriceRange(lprice, uprice);
	}
	


	@GetMapping(value = "/code")
	public ResponseEntity<Product> fetchProductByCode(@RequestHeader(value = "code") int code) {
		return productService.fetchProductByCode(code);
	}
	@GetMapping(value = "/category")
	public ResponseEntity<List<Product>> fetchByCategory(@RequestHeader(value = "category") String category) {
		return productService.fetchByCategory(category);
	}

}