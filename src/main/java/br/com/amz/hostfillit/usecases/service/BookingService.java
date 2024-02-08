package br.com.amz.hostfillit.usecases.service;

import br.com.amz.hostfillit.usecases.adapter.BookingAdapter;
import br.com.amz.hostfillit.usecases.domain.Booking;
import br.com.amz.hostfillit.usecases.exception.DateOverlapException;
import br.com.amz.hostfillit.usecases.exception.ResourceNotFoundException;
import br.com.amz.hostfillit.web.dto.booking.FillBookingDTO;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private static final Logger LOGGER = LogManager.getLogger(BookingService.class);
    private final BookingAdapter adapter;
    private final BlockService blockService;
    private final PropertyService propertyService;
    private final UserService userService;

    public BookingService(final BookingAdapter adapter,
                          final BlockService blockService,
                          final PropertyService propertyService,
                          final UserService userService) {
        this.adapter = adapter;
        this.blockService = blockService;
        this.propertyService = propertyService;
        this.userService = userService;
    }

    @Transactional
    public Booking createBooking(final FillBookingDTO bookingDto) {
        this.checkBookingOverlap(bookingDto.propertyId(), bookingDto.startDate(), bookingDto.endDate());

        final var property = propertyService.findById(bookingDto.propertyId());
        final var guest = userService.findById(bookingDto.guestId());

        final var booking = bookingDto.toModel(guest, property);
        return adapter.create(booking);
    }

    @Transactional
    public Booking updateBooking(final UUID bookingId, final FillBookingDTO bookingDto) {
        this.checkBookingOverlap(bookingDto.propertyId(), bookingDto.startDate(), bookingDto.endDate());

        final var booking = adapter.findById(bookingId);

        if (booking == null) throw new ResourceNotFoundException("Booking not found for the given ID");

        final var guest = userService.findById(bookingDto.guestId());
        final var property = propertyService.findById(bookingDto.propertyId());

        final var updatedBooking = new Booking(booking.id(), guest, property,
                bookingDto.startDate(), bookingDto.endDate(), bookingDto.isActive());

        return adapter.update(updatedBooking);
    }

    /**
     * Check if the requested booking overlaps with an existing booking or block
     *
     * @param propertyId Property ID
     * @param startDate  Start date of the requested booking
     * @param endDate    End date of the requested booking
     */
    private void checkBookingOverlap(final UUID propertyId, final LocalDate startDate, final LocalDate endDate) {
        // Check async if the requested booking overlaps with an existing booking or block
        final var bookingOverlapCheck = CompletableFuture.runAsync(() -> {
            final var existingBookings = adapter.findByPropertyId(propertyId);
            existingBookings.forEach((existingBooking) -> {
                if (existingBooking.hasDateOverlap(startDate, endDate)) {
                    throw new DateOverlapException("Requested dates overlap with an existing booking");
                }
            });
        }).exceptionally((ex) -> {
            if (ex instanceof DateOverlapException)
                throw (DateOverlapException) ex;
            throw new RuntimeException("Booking overlap check failed", ex);
        });

        final var blockOverlapCheck = CompletableFuture.runAsync(() ->
                blockService.checkBlockOverlap(propertyId, startDate, endDate)).exceptionally((ex) -> {
            if (ex instanceof DateOverlapException)
                throw (DateOverlapException) ex;
            throw new RuntimeException("Block overlap check failed", ex);
        });

        // Wait for both checks to complete
        final var asyncOverlapChecks = CompletableFuture.allOf(bookingOverlapCheck, blockOverlapCheck);

        try {
            asyncOverlapChecks.join();
        } catch (RuntimeException e) {
            LOGGER.error("Error while trying to check booking and block overlap: " + e.getMessage());
            throw e;
        }
    }
}
