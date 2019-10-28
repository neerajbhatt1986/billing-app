package com.billing.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryDTO implements Serializable {

    private Integer id;
    private String name;
    private List<ProductDTO> products = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
