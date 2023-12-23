package com.iprwc.jpgshop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private boolean isAdmin;

    @Column(nullable = false)
    @Getter
    @Setter
    private double debits = 0.0;

    public User() {}

    public User(String username, String email, String password, boolean isAdmin, double debits) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.debits = debits;
    }
}
