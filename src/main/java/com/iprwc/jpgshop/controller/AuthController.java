package com.iprwc.jpgshop.controller;

import com.iprwc.jpgshop.dao.UserDAO;
import com.iprwc.jpgshop.entity.User;
import org.passay.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserDAO userDAO;

    public AuthController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

//    public Map<String, Object> register(@RequestBody User user) {
//        if (!this.verifyEmail(User.getEmail())) {
//            throw new UnmetEmailRequirementsException();
//        }
//    }


    private boolean verifyEmail(String email) {
        final Pattern VERIFY_EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VERIFY_EMAIL.matcher(email);
        return matcher.find();
    }

    private boolean verifyPassword(String password) {
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
}
