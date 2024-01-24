package com.iprwc.jpgshop.service;

import com.iprwc.jpgshop.dao.ProductDAO;
import com.iprwc.jpgshop.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void updateProduct(Long productId, Product product) {
        Optional<Product> oldProduct = productDAO.getProductById(productId);
        if (oldProduct.isPresent()) {
            Product newProduct = oldProduct.get();
            newProduct.setName(product.getName());
            newProduct.setPictureUrl(product.getPictureUrl());
            newProduct.setPrice(product.getPrice());

            productDAO.updateProduct(newProduct);
        } else {
            System.out.println("Product with ID " + product.getId() + " not found.");
        }
    }

    public void deleteProductById(Long productId) {
        productDAO.deleteProductById(productId);
    }
}
