package com.iprwc.jpgshop.controller;

import com.iprwc.jpgshop.config.JwtFilter;
import com.iprwc.jpgshop.config.JwtToken;
import com.iprwc.jpgshop.entity.Product;
import com.iprwc.jpgshop.entity.User;
import com.iprwc.jpgshop.service.ProductService;
import com.iprwc.jpgshop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://51.38.114.113"})
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final JwtFilter jwtFilter;
    private final UserService userService;
    private final JwtToken jwtToken;
    ProductService productService;

    public ProductController(JwtFilter jwtFilter, UserService userService, JwtToken jwtToken, ProductService productService) {
        this.jwtFilter = jwtFilter;
        this.userService = userService;
        this.jwtToken = jwtToken;
        this.productService = productService;
    }

    @PostMapping("/createProduct")
    public void createProduct(HttpServletRequest request, @RequestBody Product product) {
        String token = jwtFilter.parseJwt(request);
        System.out.println(token);
        if (token != null && !token.isBlank()) {
            User user = userService.findUserByToken(token);
            System.out.println(user);
            if (user.getEmail().equals("test@test.com")) {
                productService.createProduct(product);
                return;
            }
        }
        throw new RuntimeException("Error: could not create product.");
    }

    @GetMapping("/getProducts")
    @ResponseBody
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/updateProduct/{productId}")
    public void updateProduct(HttpServletRequest request, @PathVariable Long productId, @RequestBody Product product) {
        String token = jwtFilter.parseJwt(request);
        if (token != null && !token.isBlank()) {
            User user = userService.findUserByToken(token);
            if (user.getEmail().equals("test@test.com")) {
                productService.updateProduct(productId, product);
                return;
            }
        }
        throw new RuntimeException("Error: could not update product.");
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public void deleteProduct(HttpServletRequest request, @PathVariable Long productId) {
        String token = jwtFilter.parseJwt(request);
        if (token != null && !token.isBlank()) {
            User user = userService.findUserByToken(token);
            if (user.getEmail().equals("test@test.com")) {
                productService.deleteProductById(productId);
                return;
            }
        }
        throw new RuntimeException("Error: could not delete product.");
    }
}
