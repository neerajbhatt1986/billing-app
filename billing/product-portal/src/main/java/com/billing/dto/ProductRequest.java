package com.billing.dto;

import com.sun.istack.NotNull;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

public class ProductRequest implements Serializable {

    @NotBlank
    private String name;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
