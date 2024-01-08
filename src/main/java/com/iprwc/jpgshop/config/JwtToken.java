package com.iprwc.jpgshop.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class JwtToken {

    @Value("no_one_knows_this_123!")
    private String secret;

    public String genToken(String email) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject("User")
                .withClaim("email", email)
                .withIssuer("jpgshop")
                .withIssuedAt(new Date())
                .withExpiresAt(this.expiresAtDate())
                .sign(Algorithm.HMAC256(secret));
    }

    private Date expiresAtDate() {
        int expirationHours = 6;
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(new Date());
        calendarDate.add(Calendar.HOUR, expirationHours);
        return calendarDate.getTime();
    }

    public String findUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean verifyJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}
