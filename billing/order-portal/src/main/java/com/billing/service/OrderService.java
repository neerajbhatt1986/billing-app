package com.billing.service;

import com.billing.client.ICustomerService;
import com.billing.client.IProductService;
import com.billing.dao.OrderRepository;
import com.billing.dto.OrderDTO;
import com.billing.dto.OrderRequestDTO;
import com.billing.dto.ProductDTO;
import com.billing.dto.UserDTO;
import com.billing.entity.Order;
import com.billing.entity.OrderLine;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IProductService productService;

    @Autowired
    ICustomerService customerService;

    @Override
    public OrderDTO findOrder(Integer orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("No User find for id"));
        return modelMapper.map(order, OrderDTO.class);
    }

    public List<OrderDTO> findOrderByUserId(Integer userId){
        List<Order> orderList = orderRepository.findByUserId(userId);
        return orderList.stream().map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO saveOrder(OrderRequestDTO orderRequestDTO){
        UserDTO customerDetails = customerService.getCustomerDetails(orderRequestDTO.getUserId());

        Order order = modelMapper.map(orderRequestDTO, Order.class);
        order.getOrderLines().forEach((OrderLine orderLine) -> {
            ProductDTO productDetails = productService.getProductDetails(orderLine.getProductId());
            orderLine.setAmount(productDetails.getPrice().multiply(new BigDecimal(orderLine.getQuantity())));
            orderLine.setOrder(order);
        });
        Order newOrder = orderRepository.save(order);
        return modelMapper.map(newOrder, OrderDTO.class);
    }

}
