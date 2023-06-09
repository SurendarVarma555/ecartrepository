package com.dailycodebuffer.app.controller;

import com.dailycodebuffer.app.model.ProductRequest;
import com.dailycodebuffer.app.model.ProductResponse;
import com.dailycodebuffer.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest) {
        long productId = productService.addProduct(productRequest);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId){
        ProductResponse productResponse = productService.getProductById(productId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);

    }

    /*API for Reduce the quantity of Product based on two parameters i.e.., ProductId and Quantity*/
    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity (@PathVariable("id") long productId, @RequestParam long quantity){
    productService.reduceQuantity(productId,quantity);
    return new ResponseEntity<>(HttpStatus.OK);
    }

}
