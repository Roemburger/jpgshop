package com.iprwc.jpgshop.controller;

import com.iprwc.jpgshop.entity.Product;
import com.iprwc.jpgshop.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://51.38.114.113:8080"})
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

    @PutMapping("/updateProduct/{productId}")
    public void updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        productService.updateProduct(productId, product);
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProductById(productId);
    }
}
