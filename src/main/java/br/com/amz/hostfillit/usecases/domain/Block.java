package br.com.amz.hostfillit.usecases.domain;

import br.com.amz.hostfillit.persistence.entity.BlockEntity;
import br.com.amz.hostfillit.usecases.util.DateUtils;
import java.time.LocalDate;
import java.util.UUID;

public record Block(UUID id, Property property, LocalDate startDate, LocalDate endDate) {
    public static Block fromEntity(final BlockEntity entity) {
        final var property = Property.fromEntity(entity.getProperty());

        return new Block(entity.getId(), property, entity.getStartDate(), entity.getEndDate());
    }

    public BlockEntity toEntity() {
        final var propertyEntity = this.property.toEntity();

        return new BlockEntity(this.id, propertyEntity, this.startDate, this.endDate);
    }

    public boolean hasDateOverlap(LocalDate startDate, LocalDate endDate) {
        return DateUtils.hasDateOverlap(this.startDate, this.endDate, startDate, endDate);
    }
}
