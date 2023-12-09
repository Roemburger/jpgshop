package com.iprwc.jpgshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    @Getter
    @Setter
    private RoleType type;

    public Role() {}

    public Role(RoleType type) {
        this.type = type;
    }
}
