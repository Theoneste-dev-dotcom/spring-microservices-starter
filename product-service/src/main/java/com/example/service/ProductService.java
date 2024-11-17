package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public String getProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(!product.isPresent()) {
           return null;
        }
        return product.get().getName();
    }

    public Product addProduct(Product product) {
       Product product1 = Product.builder()
               .name(product.getName())
               .price(product.getPrice())
               .description(product.getDescription())
               .build();
       productRepository.save(product1);
       return product1;
    }
}
