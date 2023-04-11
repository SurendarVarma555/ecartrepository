package com.dailycodebuffer.service;

import com.dailycodebuffer.model.PaymentRequest;

public interface PaymentService {
    long doPayment (PaymentRequest paymentRequest);
}
