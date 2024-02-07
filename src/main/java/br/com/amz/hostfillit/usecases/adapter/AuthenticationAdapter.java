package br.com.amz.hostfillit.usecases.adapter;

import br.com.amz.hostfillit.persistence.repository.AuthenticationRepository;
import br.com.amz.hostfillit.usecases.exception.ResourceNotFoundException;
import br.com.amz.hostfillit.usecases.domain.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationAdapter {

	private final AuthenticationRepository repository;

	public AuthenticationAdapter(final AuthenticationRepository repository) {
		this.repository = repository;
	}

	public Authentication findActive(final String username) throws ResourceNotFoundException {
		final var entity = repository.findByUsernameAndIsActiveTrue(username)
				.orElseThrow(() -> new ResourceNotFoundException("Couldn't find an active login for username " + username));

		return Authentication.fromEntity(entity);
	}

	public Authentication create(final Authentication authentication) {
		final var entity = repository.save(authentication.toEntity());

		return Authentication.fromEntity(entity);
	}
}
