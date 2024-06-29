/*
    Filename: Users.java
    File Updated: 2024-06-24
    Edited by: Iman Hidayat
    Description: Users Model
 */
package com.ccsd.project.model;

import com.ccsd.project.enums.Status;
import com.ccsd.project.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;
    private String username;
    private String password;
    private String email;
    private String handphone;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "address_3")
    private String address3;

    private String postcode;
    private String city;
    private String state;
    @Column(name = "role")
    private UserRole role;
    private String country;
    private Status status;
}
