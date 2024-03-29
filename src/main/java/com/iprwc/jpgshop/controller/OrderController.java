package com.iprwc.jpgshop.controller;

import com.iprwc.jpgshop.config.JwtFilter;
import com.iprwc.jpgshop.config.JwtToken;
import com.iprwc.jpgshop.entity.Order;
import com.iprwc.jpgshop.entity.User;
import com.iprwc.jpgshop.service.OrderService;
import com.iprwc.jpgshop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200", "http://51.38.114.113"})
@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final JwtFilter jwtFilter;
    private final UserService userService;
    private final JwtToken jwtToken;
    private final OrderService orderService;

    public OrderController(JwtFilter jwtFilter, UserService userService, JwtToken jwtToken, OrderService orderService) {
        this.jwtFilter = jwtFilter;
        this.userService = userService;
        this.jwtToken = jwtToken;
        this.orderService = orderService;
    }

    @PatchMapping("/createOrder")
    public void createOrder(HttpServletRequest request, @RequestBody Order order) {
        try {
            String token = jwtFilter.parseJwt(request);
            if (token != null && jwtToken.verifyJwtToken(token)) {
                User user = userService.findUserByToken(token);
                orderService.createOrder(user, order);
            }
        } catch (Exception ignored) {
            throw new RuntimeException("Error: could not create order.");
        }
    }
}
