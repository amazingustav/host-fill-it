package br.com.amz.hostfillit.usecases.adapter;

import br.com.amz.hostfillit.persistence.repository.PropertyRepository;
import br.com.amz.hostfillit.usecases.domain.Property;
import br.com.amz.hostfillit.usecases.exception.ResourceNotFoundException;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class PropertyAdapter {

	private final PropertyRepository repository;

	public PropertyAdapter(final PropertyRepository repository) {
		this.repository = repository;
	}

	public Property findById(final UUID id) {
		final var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Couldn't find a property with ID: " + id));

		return Property.fromEntity(entity);
	}

	public Property create(final Property data) {
		final var entity = repository.save(data.toEntity());

		return Property.fromEntity(entity);
	}
}
