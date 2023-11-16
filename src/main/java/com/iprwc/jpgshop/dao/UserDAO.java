package com.iprwc.jpgshop.dao;

import com.iprwc.jpgshop.entity.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDAO {

    private final UserRepository userRepository;

    public UserDAO(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

    public Optional<User> findUsernameById(long id) {
        return this.userRepository.findById(id);
    }
}
