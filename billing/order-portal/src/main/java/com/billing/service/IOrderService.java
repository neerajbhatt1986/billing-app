package com.billing.service;

import com.billing.dto.OrderDTO;
import com.billing.dto.OrderRequestDTO;

import java.util.List;

public interface IOrderService {
    OrderDTO findOrder(Integer orderId);
    List<OrderDTO> findOrderByUserId(Integer userId);
    OrderDTO saveOrder(OrderRequestDTO orderRequestDTO);
}
