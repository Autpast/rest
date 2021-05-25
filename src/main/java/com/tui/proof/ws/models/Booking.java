package com.tui.proof.ws.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Booking {

    private int id;
    private String name;
    private Flight flight;
    private String lastName;
    private String address;
    private String postCode;
    private String country;
    private String email;
    private boolean confirmed;
    private Set<String> phoneNumbers;
}
