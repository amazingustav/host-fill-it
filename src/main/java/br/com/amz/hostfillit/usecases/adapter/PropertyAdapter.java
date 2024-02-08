package br.com.amz.hostfillit.usecases.adapter;

import br.com.amz.hostfillit.persistence.repository.PropertyRepository;
import br.com.amz.hostfillit.usecases.domain.Property;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class PropertyAdapter {

	private final PropertyRepository repository;

	public PropertyAdapter(final PropertyRepository repository) {
		this.repository = repository;
	}

	public Property findById(final UUID id) {
		return repository.findById(id)
				.map(Property::fromEntity)
				.orElse(null);
	}

	public Property create(final Property data) {
		final var entity = repository.save(data.toEntity());

		return Property.fromEntity(entity);
	}
}
