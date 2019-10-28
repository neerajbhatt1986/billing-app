package com.billing.dto;

import com.sun.istack.NotNull;

import java.io.Serializable;

public class OrderLineRequestDTO implements Serializable {
    private Integer id;
    @NotNull
    private Integer productId;
    @NotNull
    private Integer quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
