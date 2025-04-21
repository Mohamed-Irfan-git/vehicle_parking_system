package com.example.vehicle_parking.frontend.Model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String nic;

    public User(String firstName, String lastName, String email, String password, String phone, String address, String nic) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.nic = nic;
    }



}
