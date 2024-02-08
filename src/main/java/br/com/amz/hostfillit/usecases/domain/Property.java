package br.com.amz.hostfillit.usecases.domain;

import br.com.amz.hostfillit.persistence.entity.PropertyEntity;
import java.util.UUID;

public record Property(UUID id, String name, String location) {
    public static Property fromEntity(final PropertyEntity entity) {
        return new Property(entity.getId(), entity.getName(), entity.getLocation());
    }

    public PropertyEntity toEntity() {
        return new PropertyEntity(this.id, this.name, this.location);
    }
}
