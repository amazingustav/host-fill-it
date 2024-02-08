package br.com.amz.hostfillit.web.dto.booking;

import br.com.amz.hostfillit.usecases.domain.Booking;
import br.com.amz.hostfillit.usecases.domain.Property;
import br.com.amz.hostfillit.usecases.domain.User;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.util.Assert;

public record CreateBookingDTO(UUID guestId, UUID propertyId, LocalDate startDate, LocalDate endDate) {

    public void validate() {
        Assert.notNull(guestId, "Guest ID must be provided");
        Assert.notNull(propertyId, "Property ID must be provided");
    }

    public Booking toModel(final User guest, final Property property) {
        return new Booking(null, guest, property, startDate, endDate, true);
    }
}

