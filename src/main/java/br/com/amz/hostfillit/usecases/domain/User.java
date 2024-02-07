package br.com.amz.hostfillit.usecases.domain;

import br.com.amz.hostfillit.persistence.entity.UserEntity;
import java.util.UUID;

public record User(UUID id, String name, String mail) {
    public static User fromEntity(final UserEntity entity) {
        return new User(entity.getId(), entity.getName(), entity.getMail());
    }

    public UserEntity toEntity() {
        return new UserEntity(this.id, this.name, this.mail);
    }
}
