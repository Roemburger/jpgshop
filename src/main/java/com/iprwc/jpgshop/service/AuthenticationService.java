package com.iprwc.jpgshop.service;

import com.iprwc.jpgshop.dao.UserDAO;
import com.iprwc.jpgshop.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserDAO userDAO;

    public AuthenticationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getAuthUser() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDAO.findByEmail(email).get();
    }
}
