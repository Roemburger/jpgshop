package com.iprwc.jpgshop.controller;

import com.iprwc.jpgshop.config.JwtToken;
import com.iprwc.jpgshop.dao.UserDAO;
import com.iprwc.jpgshop.entity.LoginCredentials;
import com.iprwc.jpgshop.entity.User;
import org.passay.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserDAO userDAO;
    private final JwtToken jwtToken;
    private final AuthenticationManager authenticationManager;

    public UserController(PasswordEncoder passwordEncoder, UserDAO userDAO, JwtToken jwtToken, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
        this.jwtToken = jwtToken;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        if (!this.isValidEmail(user.getEmail())) {
            throw new RuntimeException("Email address is already registered.");
        }

        if (!this.IsValidPassword(user.getPassword())) {
            throw new RuntimeException("Password is of incorrect format.");
        }

        Optional<User> registerUser = userDAO.findByEmail(user.getEmail());
        if (registerUser.isPresent()) {
            throw new RuntimeException("User already exists.");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setDebits(0.0);

        user = userDAO.save(user);
        String token = jwtToken.genToken(user.getEmail());
        return Collections.singletonMap("token", token);
    }

    private boolean isValidEmail(String email) {
        final Pattern VERIFY_EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VERIFY_EMAIL.matcher(email);
        return matcher.find();
    }

    private boolean IsValidPassword(String password) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(password));
        return result.isValid();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCredentials credentials) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(credentials.email, credentials.password);
            authenticationManager.authenticate(authToken);
            String token = jwtToken.genToken(credentials.email);

            Optional<User> user = userDAO.findByEmail(credentials.email);
            user.ifPresent(userDAO::save);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (AuthenticationException exception) {
            throw new RuntimeException("Login credentials are invalid.");
        }
    }
}
