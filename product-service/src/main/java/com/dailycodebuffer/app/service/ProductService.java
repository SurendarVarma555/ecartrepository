package com.dailycodebuffer.app.service;

import com.dailycodebuffer.app.model.ProductRequest;
import com.dailycodebuffer.app.model.ProductResponse;

public interface ProductService {

    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);
}
