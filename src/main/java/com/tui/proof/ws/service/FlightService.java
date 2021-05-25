package com.tui.proof.ws.service;

import com.tui.proof.ws.models.Flight;
import com.tui.proof.ws.models.FlightResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FlightService implements InitializingBean {

    private final Map<String, Flight> flights = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        Flight usaToIndia = new Flight("Any_company", "FX234",
                "Chicago", 300, 125.15, "Pune",
                LocalDateTime.of(2021, 6, 1, 12,55));
        Flight usaToIndiaSecond = new Flight("Any_company", "FX235",
                "Chicago", 300, 120, "Pune",
                LocalDateTime.of(2021, 6, 2, 12,55));
        Flight indiaToChina = new Flight("Any_company", "MN450",
                "Pune", 300, 63.50, "Shanghai",
                LocalDateTime.of(2021, 6, 1, 12,55));
        flights.put(usaToIndia.getNumber(), usaToIndia);
        flights.put(usaToIndiaSecond.getNumber(), usaToIndiaSecond);
        flights.put(indiaToChina.getNumber(), indiaToChina);
    }

    public List<FlightResponse> checkAvailability(String origin,
                                                  String destination,
                                                  LocalDate from,
                                                  LocalDate to,
                                                  Integer seatsCount) {
        long id = System.currentTimeMillis();
        return flights.values().stream()
                .filter(f -> f.getOrigin().equals(origin))
                .filter(f -> f.getDestination().equals(destination))
                .filter(f -> isDateBetween(f.getDeparture(), from, to))
                .filter(f -> f.getSeatsCount() >= seatsCount)
                .map(f -> new FlightResponse(f, id))
                .collect(Collectors.toList());
    }

    public Flight getFlight(String id) {
        return flights.get(id);
    }

    private boolean isDateBetween(LocalDateTime date, LocalDate from, LocalDate to) {
        LocalDate dateOnly = date.toLocalDate();
        return (dateOnly.isAfter(from) || dateOnly.equals(from))
                && (dateOnly.isBefore(to) || dateOnly.isEqual(to));
    }

}
