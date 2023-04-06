package com.dailycodebuffer.service;

import com.dailycodebuffer.model.OrderRequest;

public interface OrderService{
    long placeOrder(OrderRequest orderRequest);
}
