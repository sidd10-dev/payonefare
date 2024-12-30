package com.payonefare.api.common.data;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/*
Name: Driver Entity
Description: This Entity is used to store Drivers Data
*/
@Serdeable
@Entity
public class Driver {
    /*
    Description: Primary Key - ID
    Type: Long
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    Description: Driver Name
    Type: String
     */
    @Column
    @Size(min=2)
    private String name;

    /*
    Description: Driver Phone
    Type: String
     */
    @Column(unique = true)
    @Pattern(regexp="(^$|[0-9]{10})")
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(min = 2) String getName() {
        return name;
    }

    public void setName(@Size(min = 2) String name) {
        this.name = name;
    }

    public @Pattern(regexp = "(^$|[0-9]{10})") String getPhone() {
        return phone;
    }

    public void setPhone(@Pattern(regexp = "(^$|[0-9]{10})") String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
