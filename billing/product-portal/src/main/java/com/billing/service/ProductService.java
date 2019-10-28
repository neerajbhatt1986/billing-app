package com.billing.service;

import com.billing.dao.ICategoryRepository;
import com.billing.dao.IProductRepository;
import com.billing.dto.CategoryDTO;
import com.billing.dto.CategoryRequest;
import com.billing.dto.ProductDTO;
import com.billing.dto.ProductRequest;
import com.billing.entity.Category;
import com.billing.entity.Product;
import com.billing.exception.ApplicationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ICategoryRepository categoryRepository;

    @Autowired
    IProductRepository productRepository;

    @Override
    public CategoryDTO getCategory(Integer categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ApplicationException("No Categrory find for id " + categoryId));
        return modelMapper.map(category, CategoryDTO.class);
    }


    public void createCategory(CategoryRequest categoryRequest){
        Category category = modelMapper.map(categoryRequest, Category.class);
        categoryRepository.save(category);
    }

    public void createProduct(ProductRequest productRequest){
        categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new ApplicationException("No category find for id "+productRequest.getCategoryId()));
        Product product = modelMapper.map(productRequest, Product.class);
        productRepository.save(product);
    }

    public ProductDTO getProduct(Integer productId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new ApplicationException("No product find by id " + productId));
        return modelMapper.map(product, ProductDTO.class);
    }
    public ProductDTO updateProduct(Integer productId, ProductRequest productRequest){
        Product product = productRepository.findById(productId).orElseThrow(() -> new ApplicationException("No product find by id " + productId));
        modelMapper.map(productRequest, product);
        product = productRepository.save(product);

        return modelMapper.map(product, ProductDTO.class);

    }
}
