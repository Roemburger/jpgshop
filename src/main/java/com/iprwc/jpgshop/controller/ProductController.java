package com.iprwc.jpgshop.controller;

import com.iprwc.jpgshop.entity.Product;
import com.iprwc.jpgshop.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/createProduct")
    public void createProduct(@RequestBody Product product) {
        productService.createProduct(product);
    }

    @GetMapping("/getProducts")
    @ResponseBody
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }
}
