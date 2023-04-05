package com.dailycodebuffer.app.service.impl;

import com.dailycodebuffer.app.entity.Product;
import com.dailycodebuffer.app.model.ProductRequest;
import com.dailycodebuffer.app.model.ProductResponse;
import com.dailycodebuffer.app.repository.ProductRepository;
import com.dailycodebuffer.app.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/*Spring's BeanUtils provides following methods to copy property values of the given source bean into the target bean. */
import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public long addProduct(ProductRequest productRequest) {

        log.info("Adding Product...");
        Product product = Product
                .builder()
                .productName(productRequest.getProductName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);

        log.info("Product is created ");

        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Get Product for the ProductId: {}",productId);

        Product product =
                productRepository
                        .findById(productId)
                        .orElseThrow(
                                ()-> new RuntimeException("Product with given is not found"));

        ProductResponse productResponse = new ProductResponse();
        copyProperties(product,productResponse);
        return productResponse;
    }
}
