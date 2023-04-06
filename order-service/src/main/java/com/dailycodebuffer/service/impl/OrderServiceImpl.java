package com.dailycodebuffer.service.impl;

import com.dailycodebuffer.entity.Order;
import com.dailycodebuffer.model.OrderRequest;
import com.dailycodebuffer.repository.OrderRepository;
import com.dailycodebuffer.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Override
    public long placeOrder(OrderRequest orderRequest){
        log.info("Placing order request : {}",orderRequest);
//        convert order request into order entity so that we need to save at server side
        log.info("Creating Order with Status CREATED");
        Order order = Order.builder()
                .amount(orderRequest.getAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        order = orderRepository.save(order);

        return order.getId();
    }
}
