package br.com.amz.hostfillit.usecases.domain;

import br.com.amz.hostfillit.persistence.entity.BookingEntity;
import br.com.amz.hostfillit.usecases.util.DateUtils;
import java.time.LocalDate;
import java.util.UUID;

public record Booking(UUID id, User guest, Property property, LocalDate startDate, LocalDate endDate, boolean isActive) {
    public static Booking fromEntity(final BookingEntity entity) {
        final var guest = User.fromEntity(entity.getGuest());
        final var property = Property.fromEntity(entity.getProperty());

        return new Booking(entity.getId(), guest, property, entity.getStartDate(),
                entity.getEndDate(), entity.isActive());
    }

    public BookingEntity toEntity() {
        final var guestEntity = this.guest.toEntity();
        final var propertyEntity = this.property.toEntity();

        return new BookingEntity(this.id, guestEntity, propertyEntity, this.startDate, this.endDate, this.isActive);
    }

    public boolean hasDateOverlap(LocalDate startDate, LocalDate endDate) {
        return DateUtils.hasDateOverlap(this.startDate, this.endDate, startDate, endDate);
    }
}
