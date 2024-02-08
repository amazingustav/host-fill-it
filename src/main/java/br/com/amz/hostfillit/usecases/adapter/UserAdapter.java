package br.com.amz.hostfillit.usecases.adapter;

import br.com.amz.hostfillit.persistence.repository.UserRepository;
import br.com.amz.hostfillit.usecases.exception.ResourceNotFoundException;
import br.com.amz.hostfillit.usecases.domain.User;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter {

	private final UserRepository repository;

	public UserAdapter(final UserRepository repository) {
		this.repository = repository;
	}

	public User findById(final UUID id) {
		final var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Couldn't find a user using its ID: " + id));

		return User.fromEntity(entity);
	}

	public User findByMail(final String mail) {
		final var entity = repository.findByMail(mail)
				.orElseThrow(() -> new ResourceNotFoundException("Couldn't find a user using its mail: " + mail));

		return User.fromEntity(entity);
	}

	public User create(final User data) {
		final var entity = repository.save(data.toEntity());

		return User.fromEntity(entity);
	}
}
