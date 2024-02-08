package br.com.amz.hostfillit.usecases.adapter;

import br.com.amz.hostfillit.persistence.repository.UserRepository;
import br.com.amz.hostfillit.usecases.domain.User;
import br.com.amz.hostfillit.usecases.exception.ResourceNotFoundException;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter {

	private final UserRepository repository;

	public UserAdapter(final UserRepository repository) {
		this.repository = repository;
	}

	public User findById(final UUID id) {
		return repository.findById(id)
				.map(User::fromEntity)
				.orElse(null);
	}

	public User findByMail(final String mail) {
		return repository.findByMail(mail)
				.map(User::fromEntity)
				.orElse(null);
	}

	public User create(final User data) {
		final var entity = repository.save(data.toEntity());

		return User.fromEntity(entity);
	}
}
