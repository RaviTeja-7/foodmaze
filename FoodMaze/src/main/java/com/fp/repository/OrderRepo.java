package com.fp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fp.entity.Orders;

@Repository
public interface OrderRepo extends CrudRepository<Orders, Integer>{

	List<Orders> findAll();
	
	Orders findById(int id);
	
	List<Orders> findByOrderDate(LocalDate orderDate);

	List<Orders> findByUserId(int userId);
	
	//Count orders by status
    @Query("SELECT COUNT(o) FROM Orders o WHERE o.status = :status")
    long countByStatus(@Param("status") Orders.OrderStatus status);

    //Calculate total revenue (sum of total_amt)
    @Query("SELECT SUM(o.total_amt) FROM Orders o")
    Double sumTotalAmount();
}