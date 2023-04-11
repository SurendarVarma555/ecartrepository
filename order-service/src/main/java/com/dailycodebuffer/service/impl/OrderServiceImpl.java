package com.dailycodebuffer.service.impl;

import com.dailycodebuffer.entity.Order;
import com.dailycodebuffer.external.client.PayementService;
import com.dailycodebuffer.external.client.ProductService;
import com.dailycodebuffer.external.client.request.PaymentRequest;
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

    @Autowired
    private ProductService productService;

    @Autowired
    private PayementService payementService;

    @Override
    public long placeOrder(OrderRequest orderRequest){

        log.info("Placing order request : {}",orderRequest);

        // Rest Api call using feign client
        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());

        log.info("Creating order with status CREATED... ");

        //convert order request into order entity so that we need to save at server side
        log.info("Creating Order with Status CREATED");
        Order order = Order.builder()
                .amount(orderRequest.getAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        order = orderRepository.save(order);
        log.info("Calling Payment service to complete the payment");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                                        .orderId(order.getId())
                                        .paymentMode(orderRequest.getPaymentMode())
                                        .amount(orderRequest.getAmount())
                                        .build();
        String orderStatus = null;
        try {
            payementService.doPayment(paymentRequest);
            log.info("Payment done successfully and Order status changing to PLACED");
            orderStatus = "PLACED";
        } catch (Exception e){
            log.info("Error occured in Payment and Order status changing to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        log.info("Order placed successfully with Order Id: {}", order.getId());
        return order.getId();
    }
}
