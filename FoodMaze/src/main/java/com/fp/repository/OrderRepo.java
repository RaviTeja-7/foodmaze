package com.fp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fp.entity.Orders;

@Repository
public interface OrderRepo extends CrudRepository<Orders, Integer>{

	List<Orders> findAll();
	
	Orders findById(int id);
	
	List<Orders> findByOrderDate(LocalDate orderDate);

	List<Orders> findByUserId(int userId);

}