package com.iprwc.jpgshop.service;

import com.iprwc.jpgshop.dao.UserDAO;
import com.iprwc.jpgshop.entity.Order;
import com.iprwc.jpgshop.entity.User;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final UserDAO userDAO;

    public OrderService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void createOrder(User user, Order order) {
        user.setDebits(user.getDebits() + order.getDebits());
        userDAO.save(user);
    }
}
