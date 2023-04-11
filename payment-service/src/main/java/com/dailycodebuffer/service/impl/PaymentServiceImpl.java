package com.dailycodebuffer.service.impl;

import com.dailycodebuffer.enitity.TransactionDetails;
import com.dailycodebuffer.model.PaymentRequest;
import com.dailycodebuffer.repository.TransactionDetailsRepository;
import com.dailycodebuffer.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TransactionDetailsRepository paymentRepository;

    @Override
    public long doPayment (PaymentRequest paymentRequest){

        log.info("Regarding Payment Details : {}",paymentRequest);
        TransactionDetails  transactionDetails = TransactionDetails.builder()
                                                .amount(paymentRequest.getAmount())
                                                .paymentDate(Instant.now())
                                                .paymentMode(paymentRequest.getPaymentMode().name())
                                                .paymentStatus("SUCCESS")
                                                .orderId(paymentRequest.getOrderId())
                                                .referenceNumber(paymentRequest.getReferenceNumber())
                                                .build();
        paymentRepository.save(transactionDetails);
        log.info("Transaction Completed with Id: {}", transactionDetails.getId());
        return transactionDetails.getId();
    }
}
