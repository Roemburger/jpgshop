package com.iprwc.jpgshop.controller;

import com.iprwc.jpgshop.config.JwtFilter;
import com.iprwc.jpgshop.config.JwtToken;
import com.iprwc.jpgshop.entity.User;
import com.iprwc.jpgshop.service.OrderService;
import com.iprwc.jpgshop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cart")
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
    public void createOrder(HttpServletRequest request, @RequestBody double newDebits) {
        try {
            String token = jwtFilter.parseJwt(request);
            if (jwtToken.verifyTokenAndGetClaim(token) != null) {
                User user = userService.findUserByToken(token);
                orderService.createOrder(user, newDebits);
            }
        } catch (Exception ignored) {
        }
    }
}
