package com.fp.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.fp.entity.Orders;
import com.fp.entity.Orders.OrderStatus;
import com.fp.entity.User;
import com.fp.repository.OrderRepo;
import com.fp.repository.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	private OrderRepo orderRepo;
	private UserRepo userRepo;
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

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
			return new ResponseEntity<>("Order created Successfully!", HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Failed to create order: User not found or request failed");
	}

	public ResponseEntity<List<Orders>> fetchAllOrders() {
		return new ResponseEntity<>(orderRepo.findAll(), HttpStatus.OK);
	}

	public ResponseEntity<Orders> fetchOrderById(int id) {
		Orders order = orderRepo.findById(id);
		if (order != null) {
			return new ResponseEntity<>(order, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<List<Orders>> fetchOrderbyUserId(int id) {
		return new ResponseEntity<>(orderRepo.findByUserId(id), HttpStatus.OK);
	}

	public ResponseEntity<List<Orders>> fetchOrderByOrderDate(LocalDate orderDate) {
		return new ResponseEntity<>(orderRepo.findByOrderDate(orderDate), HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<String> updateOrderStatus(int id, String status) {
	    Optional<Orders> optionalOrder = Optional.ofNullable(orderRepo.findById(id));

	    if (optionalOrder.isPresent()) {
	        Orders order = optionalOrder.get();

	        // ✅ Prevent updating a Completed or Cancelled order
	        if (order.getStatus().name().equalsIgnoreCase("Completed") || order.getStatus().name().equalsIgnoreCase("Cancelled")) {
	            return new ResponseEntity<>("Order status cannot be changed after it is Completed or Cancelled!", HttpStatus.BAD_REQUEST);
	        }

	        try {
	            // ✅ Trim and parse status safely
	            OrderStatus newStatus = OrderStatus.valueOf(status.trim().replace("\"", ""));
	            order.setStatus(newStatus);
	            orderRepo.save(order);

	            // ✅ FORCE CACHE INVALIDATION BY CALLING ANALYTICS AGAIN
	            getOrderAnalytics();

	            return new ResponseEntity<>("Order Status Updated Successfully!", HttpStatus.OK);
	        } catch (IllegalArgumentException e) {
	            return new ResponseEntity<>("Invalid status value!", HttpStatus.BAD_REQUEST);
	        }
	    } else {
	        return new ResponseEntity<>("Order Not Found!", HttpStatus.NOT_FOUND);
	    }
	}

	
	public ResponseEntity<Map<String, Object>> getOrderAnalytics() {
	    Map<String, Object> analyticsData = new HashMap<>();
	    List<Orders> orders = orderRepo.findAll(); // Fetch latest orders

	    logger.info("Fetching latest order analytics...");

	    int totalOrders = orders.size();
	    double totalRevenue = orders.stream().mapToDouble(Orders::getTotal_amt).sum();
	    
	    long pendingOrders = orders.stream().filter(o -> {
	        boolean isPending = o.getStatus().name().equalsIgnoreCase("Pending");
	        logger.info("Order ID: " + o.getOrderId() + " | Status: " + o.getStatus() + " | Is Pending: " + isPending);
	        return isPending;
	    }).count();

	    long completedOrders = orders.stream().filter(o -> {
	        boolean isCompleted = o.getStatus().name().equalsIgnoreCase("Completed");
	        logger.info("Order ID: " + o.getOrderId() + " | Status: " + o.getStatus() + " | Is Completed: " + isCompleted);
	        return isCompleted;
	    }).count();

	    long cancelledOrders = orders.stream().filter(o -> {
	        boolean isCancelled = o.getStatus().name().equalsIgnoreCase("Cancelled");
	        logger.info("Order ID: " + o.getOrderId() + " | Status: " + o.getStatus() + " | Is Cancelled: " + isCancelled);
	        return isCancelled;
	    }).count();

	    // Monthly Sales Data - Format "YYYY-MM"
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
	    Map<String, Double> monthlySales = orders.stream()
	        .collect(Collectors.groupingBy(o -> o.getOrderDate().format(formatter),
	                Collectors.summingDouble(Orders::getTotal_amt)));

	    analyticsData.put("totalOrders", totalOrders);
	    analyticsData.put("totalRevenue", totalRevenue);
	    analyticsData.put("pendingOrders", pendingOrders);
	    analyticsData.put("completedOrders", completedOrders);
	    analyticsData.put("cancelledOrders", cancelledOrders);
	    analyticsData.put("monthlySales", monthlySales);

	    logger.info("Analytics Data: " + analyticsData);
	    
	    return new ResponseEntity<>(analyticsData, HttpStatus.OK);
	}

}
