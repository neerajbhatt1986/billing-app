package com.billing.controller;

import com.billing.dto.CategoryDTO;
import com.billing.dto.CategoryRequest;
import com.billing.dto.ProductDTO;
import com.billing.dto.ProductRequest;
import com.billing.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.PUT;

@RestController
public class ProductController {

    @Autowired
    IProductService productService;


    @PostMapping("category/create")
    public void addCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        productService.createCategory(categoryRequest);
    }

    @GetMapping("category/{categoryId}")
    public CategoryDTO findCategory(@PathVariable Integer categoryId){
        return productService.getCategory(categoryId);
    }

    @PostMapping("product/create")
    public void addProduct(@Valid @RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }

    @GetMapping("product/{productId}")
    public ProductDTO findProduct(@PathVariable Integer productId){
        return productService.getProduct(productId);
    }

    @PutMapping("product/{productId}/update")
    public ProductDTO updateProduct(@PathVariable Integer productId, @Valid @RequestBody ProductRequest productRequest){
         return productService.updateProduct(productId, productRequest);
    }



}
