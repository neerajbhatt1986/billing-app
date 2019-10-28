package com.billing.service;

import com.billing.dto.CategoryDTO;
import com.billing.dto.CategoryRequest;
import com.billing.dto.ProductDTO;
import com.billing.dto.ProductRequest;

public interface IProductService {

    CategoryDTO getCategory(Integer categoryId);
    void createCategory(CategoryRequest categoryRequest);
    void createProduct(ProductRequest productRequest);
    ProductDTO updateProduct(Integer productId, ProductRequest productRequest);
    ProductDTO getProduct(Integer productId);
}
