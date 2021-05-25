package com.tui.proof.ws.models;

import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class FlightResponse {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("hh:mm");

    private String company;
    private String number;
    private String date;
    private String time;
    private double price;
    private Long checkId;

    public FlightResponse(Flight flight, Long checkId) {
        this.company = flight.getCompany();
        this.number = flight.getNumber();
        this.date = flight.getDeparture().toLocalDate().toString();
        this.time = flight.getDeparture().toLocalTime().format(FORMATTER);
        this.price = flight.getPrice();
        this.checkId = checkId;
    }

}
