package com.billing.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Optional;

@Entity
@Table(name = "order_line")
public class OrderLine {
    @Id @GeneratedValue
    private Integer id;
    private Integer productId;
    private Integer quantity;
    private BigDecimal amount;
    @ManyToOne
    private Order order;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Optional<Order> getOrder() {
        return Optional.of(order);
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
