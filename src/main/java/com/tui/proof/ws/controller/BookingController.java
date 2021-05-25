package com.tui.proof.ws.controller;

import com.tui.proof.ws.models.Booking;
import com.tui.proof.ws.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class BookingController {

    private static final long EXPIRATION_SHIFT = 900000;

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(value = "/bookings", consumes = APPLICATION_FORM_URLENCODED_VALUE, produces = APPLICATION_JSON_VALUE)
    public Booking create(@RequestParam String name,
                          @RequestParam String lastName,
                          @RequestParam String address,
                          @RequestParam String postCode,
                          @RequestParam String country,
                          @RequestParam String email,
                          @RequestParam String telephones) {
        return bookingService
                .create(name, lastName, address, postCode, country, email, telephones);
    }

    @GetMapping(value = "/bookings/{id}", produces = APPLICATION_JSON_VALUE)
    public Booking getById(@PathVariable Integer id) {
        return bookingService.get(id);
    }

    @DeleteMapping(value = "/bookings/{id}", produces = APPLICATION_JSON_VALUE)
    public Booking deleteById(@PathVariable Integer id) {
        return bookingService.delete(id);
    }

    @DeleteMapping(value = "/bookings/{id}/flight", produces = APPLICATION_JSON_VALUE)
    public Booking updateFlight(@PathVariable Integer id) {
        return bookingService.removeFlight(id);
    }

    @PatchMapping(value = "/bookings/{id}/flight", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> updateFlight(@PathVariable Integer id,
                                               @RequestParam String flightId,
                                               @RequestParam Long checkId) {
        if (System.currentTimeMillis() < checkId + EXPIRATION_SHIFT) {
            Booking booking = bookingService.addFlight(id, flightId);
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/bookings/{id}/confirm", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> confirm(@PathVariable Integer id) {
        Booking booking = bookingService.get(id);
        if (booking.getFlight() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(bookingService.confirm(id), HttpStatus.OK);
        }
    }

}
