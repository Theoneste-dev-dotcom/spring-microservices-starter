package com.example.controller;

import com.example.model.Product;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/{product_id}")
    public String getProduct(@PathVariable("product_id") Long product_id) {
        return productService.getProduct(product_id);
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);

    }


}
