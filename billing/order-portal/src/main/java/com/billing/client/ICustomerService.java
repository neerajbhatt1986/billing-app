package com.billing.client;

import com.billing.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "customer-portal-feign", url = "${customer.portal.url}")
public interface ICustomerService {

    @GetMapping("user/details/{userId}")
    UserDTO getCustomerDetails(@PathVariable Integer userId);

}
