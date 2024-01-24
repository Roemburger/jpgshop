package com.iprwc.jpgshop.dao;

import com.iprwc.jpgshop.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductDAO {

    ProductRepository productRepository;

    public ProductDAO(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public Optional<Product> getProductById(Long productId) {
        return this.productRepository.findById(productId);
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProductById(Long productId) {
        productRepository.deleteById(productId);
    }
}
