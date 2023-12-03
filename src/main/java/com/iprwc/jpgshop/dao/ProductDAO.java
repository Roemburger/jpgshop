package com.iprwc.jpgshop.dao;

import com.iprwc.jpgshop.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDAO {

    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }
}
