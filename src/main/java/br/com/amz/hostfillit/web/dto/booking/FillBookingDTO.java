package br.com.amz.hostfillit.web.dto.booking;

import br.com.amz.hostfillit.usecases.domain.Booking;
import br.com.amz.hostfillit.usecases.domain.Property;
import br.com.amz.hostfillit.usecases.domain.User;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.util.Assert;

public record FillBookingDTO(UUID guestId, UUID propertyId, LocalDate startDate, LocalDate endDate, boolean isActive) {

    public void validate() {
        Assert.notNull(guestId, "Guest ID must be provided");
        Assert.notNull(propertyId, "Property ID must be provided");
        Assert.notNull(startDate, "Start date must be provided");
        Assert.notNull(endDate, "End date must be provided");

        Assert.isTrue(!startDate.isAfter(endDate), "Start date must be on or before the end date");

        LocalDate today = LocalDate.now();
        Assert.isTrue(!startDate.isBefore(today), "Start date cannot be in the past");
    }

    public Booking toModel(final User guest, final Property property) {
        return new Booking(null, guest, property, startDate, endDate, isActive);
    }
}

