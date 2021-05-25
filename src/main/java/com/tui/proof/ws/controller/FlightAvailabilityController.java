package com.tui.proof.ws.controller;

import com.tui.proof.ws.models.FlightResponse;
import com.tui.proof.ws.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class FlightAvailabilityController {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final FlightService flightService;

    @Autowired
    public FlightAvailabilityController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping(value = "/flights", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<FlightResponse> flightsAvailability(@RequestParam String origin,
                                                    @RequestParam String destination,
                                                    @RequestParam String from,
                                                    @RequestParam String to,
                                                    @RequestParam Integer adults,
                                                    @RequestParam(required = false, defaultValue = "0") Integer infants,
                                                    @RequestParam(required = false, defaultValue = "0") Integer children) {
        LocalDate  fromParsed = LocalDate .parse(from, formatter);
        LocalDate toParsed = LocalDate .parse(to, formatter);
        int seatsCount = adults + children + infants;
        return flightService.checkAvailability(origin, destination, fromParsed, toParsed, seatsCount);
    }

}
