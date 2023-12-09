package com.iprwc.jpgshop.service;

import com.iprwc.jpgshop.dao.UserDAO;
import com.iprwc.jpgshop.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> requestedUser = userDAO.findByEmail(email);

        if (requestedUser.isEmpty()) {
            throw new UsernameNotFoundException("No user found");
        }

        User user = requestedUser.get();
        return new org.springframework.security.core.userdetails.User(
                email, user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    public User findUserByToken(String token) {
        return this.userDAO.findUserByJwtToken(token);
    }

}
