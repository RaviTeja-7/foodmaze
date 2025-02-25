package com.fp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fp.entity.Orders;
import com.fp.entity.User;
import com.fp.repository.OrderRepo;
import com.fp.repository.UserRepo;

//import jakarta.transaction.Transactional;

@Service
public class OrderService {

	private OrderRepo orderRepo;
	private UserRepo userRepo;

	public OrderService(OrderRepo orderRepo, UserRepo userRepo) {
		this.orderRepo = orderRepo;
		this.userRepo = userRepo;
	}

	public ResponseEntity<String> createOrder(Orders order) {
		User user = userRepo.findByUserId(order.getUserId());
		if (user != null) {
			order.setCustomerName(user.getUserName());
			order.setCustomerAddress(user.getUserAddress());
			order.setCustomerPhone(user.getUserPhone());
			order.setOrderDate(LocalDate.now());
			orderRepo.save(order);
			return new ResponseEntity<String>("Order created Successfully!", HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Failed to create order: User not found or request failed");
	}
	
	public ResponseEntity<List<Orders>> fetchAllOrders() {
		return new ResponseEntity<List<Orders>>(orderRepo.findAll(), HttpStatus.OK);
	}
	
	public ResponseEntity<Orders> fetchOrderById(int id) {
		return new ResponseEntity<Orders>(orderRepo.findById(id), HttpStatus.OK);
	}
	
	
}