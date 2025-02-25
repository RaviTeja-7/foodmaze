package com.fp.controller;

//import java.time.LocalDate;
//import java.util.List;

import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
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
}
