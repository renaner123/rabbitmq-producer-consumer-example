package com.producer.demo.dto;

import java.io.Serial;
import java.io.Serializable;

public class OrderDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String orderId;
    private Integer quantity;

    private String description;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, Integer quantity, String description) {
        this.orderId = orderId;
        this.quantity = quantity;
        this.description = description;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                '}';
    }
}
