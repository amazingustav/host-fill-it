package br.com.amz.hostfillit.usecases.domain;

import br.com.amz.hostfillit.persistence.entity.AuthenticationEntity;
import java.util.UUID;

public record Authentication(UUID id, String username, String password, boolean isActive, User user) {

	public static Authentication fromEntity(final AuthenticationEntity entity) {
		final var user = User.fromEntity(entity.getUserEntity());
		return new Authentication(entity.getId(), entity.getUsername(),
				entity.getPassword(), entity.isIsActive(), user);
	}

	public AuthenticationEntity toEntity() {
		return new AuthenticationEntity(this.id, this.username, this.password, this.isActive, this.user.toEntity());
	}
}
