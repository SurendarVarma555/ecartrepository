package com.dailycodebuffer.app.service.impl;

import com.dailycodebuffer.app.entity.Product;
import com.dailycodebuffer.app.exception.ProductServiceCustomException;
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
                                ()-> new ProductServiceCustomException("Product with given Id: "+productId+" "+"is Not Found", "PRODUCT_NOT_FOUND"));

        ProductResponse productResponse = new ProductResponse();
        //copying entity fields into target
        copyProperties(product,productResponse);
        return productResponse;
    }
    /* 1.
    * */
    @Override
    public void reduceQuantity (long productId, long quantity){
        log.info("Reduce the quantity {} for Id: {}",productId,quantity);

        /*Finding the product with given id if its found ok but
        if its doesn't exist then we have to throw an exception*/

        Product product = productRepository.findById(productId)
                .orElseThrow(()->
                        new ProductServiceCustomException("Product with given Id not Found","PRODUCT_NOT_FOUND"));

        /*If the quantity of the product is sufficient
        we have to throw an user defined exception */

        if(product.getQuantity()<quantity){
            throw new ProductServiceCustomException("Product does not have sufficient quantity","INSUFFICIENT_QUANTITY");
        }

        /*If not error and if we got product with given id then
        we have to reduce the quantity from total quantity */

        product.setQuantity(product.getQuantity()-quantity);
        productRepository.save(product);

        log.info("Product Quantity Updated successfully....");

    }
}
