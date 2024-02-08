package br.com.amz.hostfillit.web.controller;

import br.com.amz.hostfillit.usecases.exception.DateOverlapException;
import br.com.amz.hostfillit.usecases.exception.ResourceNotFoundException;
import br.com.amz.hostfillit.usecases.service.BookingService;
import br.com.amz.hostfillit.web.dto.ResponseCreated;
import br.com.amz.hostfillit.web.dto.ResponseMessage;
import br.com.amz.hostfillit.web.dto.booking.FillBookingDTO;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<?> createBooking(@RequestBody final FillBookingDTO dto) {
        try {
            dto.validate();
            final var createdBooking = service.createBooking(dto);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseCreated(createdBooking.id()));
        } catch (Exception e) {
            if (e instanceof DateOverlapException) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(e.getMessage()));
            }

            if (e instanceof IllegalArgumentException) {
                return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
            }

            final var message = "Error while trying to create a booking";
            return ResponseEntity.internalServerError().body(new ResponseMessage(message));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable final UUID id, @RequestBody final FillBookingDTO dto) {
        try {
            dto.validate();
            final var updatedBooking = service.updateBooking(id, dto);

            return ResponseEntity.status(HttpStatus.OK).body(updatedBooking);
        } catch (Exception e) {
            if (e instanceof DateOverlapException) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(e.getMessage()));
            }

            if (e instanceof ResourceNotFoundException) {
                return ResponseEntity.notFound().build();
            }

            if (e instanceof IllegalArgumentException) {
                return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
            }

            final var message = "Error while trying to update a booking";
            return ResponseEntity.internalServerError().body(new ResponseMessage(message));
        }
    }
}
