package com.tui.proof.ws.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Flight {

    private String company;
    private String number;
    private String origin;
    private int seatsCount;
    private double price;
    private String destination;
    private LocalDateTime departure;

}
