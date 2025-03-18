package com.fp.entity;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private LocalDate orderDate;
    private String orderCode;
    private String orderName;
    private String qty;
    private double total_amt;

    @Enumerated(EnumType.STRING)  // ✅ Ensures values are stored correctly as plain strings
    private OrderStatus status;

    private String customerName;
    private String customerAddress;
    private long customerPhone;
    private int userId;

    public Orders() {
        this.status = OrderStatus.Pending;  // ✅ Default status when creating an order
    }

    public Orders(int orderId, LocalDate orderDate, String orderCode, String orderName, String qty,
                  double total_amt, OrderStatus status, String customerName, String customerAddress, long customerPhone, int userId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderCode = orderCode;
        this.orderName = orderName;
        this.qty = qty;
        this.total_amt = total_amt;
        this.status = status;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public double getTotal_amt() {
        return total_amt;
    }

    public void setTotal_amt(double total_amt) {
        this.total_amt = total_amt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(long customerPhone) {
        this.customerPhone = customerPhone;
    }

    @Override
    public String toString() {
        return "Orders [id=" + orderId + ", orderDate=" + orderDate + ", orderCode=" + orderCode + ", orderName="
                + orderName + ", qty=" + qty + ", total_amt=" + total_amt + ", status=" + status
                + ", customerName=" + customerName + ", customerAddress=" + customerAddress + ", customerPhone="
                + customerPhone + "]";
    }

    // ✅ Embedded Enum for Status
    public enum OrderStatus {
        Pending,
        Completed,
        Cancelled
    }
}
