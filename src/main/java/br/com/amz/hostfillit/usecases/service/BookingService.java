package br.com.amz.hostfillit.usecases.service;

import br.com.amz.hostfillit.usecases.adapter.BookingAdapter;
import br.com.amz.hostfillit.usecases.domain.Booking;
import br.com.amz.hostfillit.web.dto.booking.CreateBookingDTO;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

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
    public Booking createBooking(CreateBookingDTO bookingDto) {
        this.checkBookingOverlap(bookingDto.propertyId(), bookingDto.startDate(), bookingDto.endDate());

        blockService.checkBlockOverlap(bookingDto.propertyId(), bookingDto.startDate(), bookingDto.endDate());

        final var property = propertyService.findById(bookingDto.propertyId());
        final var guest = userService.findById(bookingDto.guestId());

        final var Booking = bookingDto.toModel(guest, property);
        return adapter.create(Booking);
    }

    /**
     * Check if the requested booking overlaps with an existing booking
     *
     * @param propertyId Property ID
     * @param startDate  Start date of the requested booking
     * @param endDate    End date of the requested booking
     */
    private void checkBookingOverlap(final UUID propertyId, final LocalDate startDate, final LocalDate endDate) {
        final var existingBookings = adapter.findByPropertyId(propertyId);

        existingBookings.forEach((existingBooking) -> {
            if (existingBooking.hasDateOverlap(startDate, endDate)) {
                throw new IllegalStateException("Requested dates overlap with an existing booking");
            }
        });
    }
}
