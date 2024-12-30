package com.payonefare.api.common.data;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/*
Name: User Entity
Description: This Entity is used to store Customer Data
*/
@Serdeable
@Entity
public class Customer {
    /*
    Description: Primary Key - ID
    Type: Long
    */
    @Id
    @GeneratedValue
    private Long id;

    /*
    Description: Name of the user
    Type: String
    */
    @Column
    @Size(min=2)
    private String name;

    /*
    Description: User mobile number
    Type: String
    */
    @Pattern(regexp="(^$|[0-9]{10})")
    @Column(unique = true)
    private String phone;

    public Long getId() {
        return id;
    }

    public @Size(min = 2) String getName() {
        return name;
    }

    public @Pattern(regexp = "(^$|[0-9]{10})") String getPhone() {
        return phone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(@Size(min = 2) String name) {
        this.name = name;
    }

    public void setPhone(@Pattern(regexp = "(^$|[0-9]{10})") String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
