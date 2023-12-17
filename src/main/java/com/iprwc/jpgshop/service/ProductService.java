package com.iprwc.jpgshop.service;

import com.iprwc.jpgshop.dao.ProductDAO;
import com.iprwc.jpgshop.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public void createProduct(Product product) {
        product.setQuantity(25);
        productDAO.createProduct(product);
    }
}
