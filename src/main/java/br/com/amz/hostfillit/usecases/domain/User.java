package br.com.amz.hostfillit.usecases.domain;

import br.com.amz.hostfillit.persistence.entity.UserEntity;
import java.util.UUID;
import java.time.Instant;

public record User(UUID id, String name, String mail, Instant createdAt, Instant updatedAt) {
    public static User fromEntity(final UserEntity entity) {
        return new User(entity.getId(), entity.getName(), entity.getMail(), entity.getCreatedAt(), entity.getUpdatedAt());
    }

    public UserEntity toEntity() {
        return new UserEntity(this.id, this.name, this.mail, this.createdAt, this.updatedAt);
    }
}
