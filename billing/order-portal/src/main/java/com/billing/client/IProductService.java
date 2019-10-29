package com.billing.client;

import com.billing.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "product-portal-feign", url = "${product.portal.url}")
public interface IProductService {

    @GetMapping("product/{productId}")
    ProductDTO getProductDetails(@PathVariable Integer productId);
}
