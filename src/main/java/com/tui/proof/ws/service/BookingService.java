package com.tui.proof.ws.service;

import com.tui.proof.ws.models.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BookingService {

    private final FlightService flightService;

    @Autowired
    public BookingService(FlightService flightService) {
        this.flightService = flightService;
    }

    private final AtomicInteger ids = new AtomicInteger(0);

    private final Map<Integer, Booking> bookings = new HashMap<>();

    public Booking create(String name,
                       String lastName,
                       String address,
                       String postCode,
                       String country,
                       String email,
                       String phoneNumbers) {
        int id = ids.incrementAndGet();
        Set<String> numbers = StringUtils.commaDelimitedListToSet(phoneNumbers);
        Booking booking = new Booking(id, name, null, lastName, address, postCode,
                country, email, false, numbers);
        bookings.put(booking.getId(), booking);
        return bookings.get(id);
    }

    public Booking get(Integer id) {
        return bookings.get(id);
    }

    public Booking delete(Integer id) {
        return bookings.remove(id);
    }

    public Booking removeFlight(Integer id) {
        Booking booking = bookings.get(id);
        booking.setFlight(null);
        return booking;
    }

    public Booking addFlight(Integer id, String flightId) {
        Booking booking = bookings.get(id);
        booking.setFlight(flightService.getFlight(flightId));
        return booking;
    }

    public Booking confirm(Integer id) {
        Booking booking = bookings.get(id);
        booking.setConfirmed(true);
        return booking;
    }


}
