package com.fp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fp.entity.Orders;
import com.fp.service.OrderService;

@RestController
@RequestMapping(path = "/api/order")
public class OrderController {

	public OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	public ResponseEntity<String> createOrder(@RequestBody Orders order) {
		return orderService.createOrder(order);
	}
	
	@GetMapping
	public ResponseEntity<List<Orders>> fetchAllOrders() {
		return orderService.fetchAllOrders();
	}
	
	@GetMapping(value = "/id")
	public ResponseEntity<Orders> fetchOrderById(@RequestHeader(value = "id") int id) {
		return orderService.fetchOrderById(id);
	}
	
	@GetMapping(value = "/userId")
	public ResponseEntity<List<Orders>> fetchOrderbyUserId(@RequestHeader(value = "userId") int id) {
		return orderService.fetchOrderbyUserId(id);
	}

	@GetMapping(value = "/date")
	public ResponseEntity<List<Orders>> fetchOrderByOrderDate(@RequestHeader(value = "orderDate") LocalDate orderDate) {
		return orderService.fetchOrderByOrderDate(orderDate);
	}
	
	@PutMapping(value = "/status")
	public ResponseEntity<String> updateOrderStatus(@RequestHeader(value = "id") int id,
			@RequestHeader(value = "status") String status) {
		return orderService.updateOrderStatus(id, status);
	}
}
