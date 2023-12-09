package com.iprwc.jpgshop.dao;

import com.iprwc.jpgshop.config.JwtToken;
import com.iprwc.jpgshop.entity.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDAO {

    private final UserRepository userRepository;
    private final JwtToken jwtToken;

    public UserDAO(UserRepository userRepository, JwtToken jwtToken) {
        this.userRepository = userRepository;
        this.jwtToken = jwtToken;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

    public User findUserByJwtToken(String token) {
        String username = jwtToken.findUserNameFromJwtToken(token);
        return this.userRepository.findByUsername(username);
    }
}

