package com.billing.controller;

import com.billing.dto.OrderDTO;
import com.billing.dto.OrderRequestDTO;
import com.billing.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    IOrderService orderService;

    @PostMapping("order/save")
    public OrderDTO saveOrder(@Valid @RequestBody OrderRequestDTO orderDTO){
        return orderService.saveOrder(orderDTO);
    }

    @GetMapping("order/{orderId}/find")
    public OrderDTO getOrder(@PathVariable Integer orderId){
        return orderService.findOrder(orderId);
    }

    @GetMapping("user/{userId}/order")
    public List<OrderDTO> findOrdersByUser(@PathVariable Integer userId){
        return orderService.findOrderByUserId(userId);
    }

}
