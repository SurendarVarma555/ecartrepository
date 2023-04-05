package com.dailycodebuffer.app.service.impl;

import com.dailycodebuffer.app.entity.Product;
import com.dailycodebuffer.app.model.ProductRequest;
import com.dailycodebuffer.app.repository.ProductRepository;
import com.dailycodebuffer.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public long addProduct(ProductRequest productRequest) {
        Product product = Product
                .builder()
                .productName(productRequest.getProductName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        return product.getProductId();
    }
}
