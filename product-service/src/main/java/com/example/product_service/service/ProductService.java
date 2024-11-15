package com.example.product_service.service;

import com.example.product_service.model.Product;
import com.example.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }


    public Product updateProduct(String id, Product productDetails) {
        return productRepository.findById(id)
                .map(product -> {
                  product.setName(productDetails.getName());
                  product.setDescription(productDetails.getDescription());
                  product.setPrice(productDetails.getPrice());
                  return productRepository.save(product);
                })
                .orElseThrow(()-> new RuntimeException("Product not found with id"));
    }
    public Product createProduct(Product productRequest) {
        Product product = new Product();
        product.setDescription(productRequest.getDescription());
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());

        productRepository.save(product);
        return product;

    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
