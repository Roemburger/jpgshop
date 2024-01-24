package com.iprwc.jpgshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String streetName;

    @Getter
    @Setter
    private Integer houseNumber;

    @Getter
    @Setter
    private String zipcode;

    @Getter
    @Setter
    private String country;

    @Column(nullable = false)
    @Getter
    @Setter
    private Double debits = 0.0;

    public Order() {}

    public Order(
            String firstName,
            String lastName,
            String streetName,
            Integer houseNumber,
            String zipcode,
            String country,
            Double debits) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.zipcode = zipcode;
        this.country = country;
        this.debits = debits;
    }
}
