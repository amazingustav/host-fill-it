package br.com.amz.hostfillit.web.controller;

import br.com.amz.hostfillit.usecases.service.BookingService;
import br.com.amz.hostfillit.web.dto.ResponseCreated;
import br.com.amz.hostfillit.web.dto.ResponseMessage;
import br.com.amz.hostfillit.web.dto.booking.CreateBookingDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService service;

    public BookingController(final BookingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody final CreateBookingDTO dto) {
        try {
            dto.validate();
            final var createdBooking = service.createBooking(dto);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseCreated(createdBooking.id()));
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
            }

            final var message = "Error while trying to create a booking";
            return ResponseEntity.internalServerError().body(new ResponseMessage(message));
        }
    }
}
