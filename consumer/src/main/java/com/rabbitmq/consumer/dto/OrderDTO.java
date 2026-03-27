package com.rabbitmq.consumer.dto;

import java.io.Serial;
import java.io.Serializable;

public class OrderDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String orderId;
    private String description;
    private Integer quantity;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, String product, Integer quantity) {
        this.orderId = orderId;
        this.description = product;
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

