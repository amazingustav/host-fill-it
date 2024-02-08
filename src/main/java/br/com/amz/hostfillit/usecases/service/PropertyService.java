package br.com.amz.hostfillit.usecases.service;

import br.com.amz.hostfillit.usecases.adapter.PropertyAdapter;
import br.com.amz.hostfillit.usecases.domain.Property;
import br.com.amz.hostfillit.usecases.exception.ResourceNotFoundException;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    private final PropertyAdapter adapter;

    public PropertyService(final PropertyAdapter adapter) {
        this.adapter = adapter;
    }

    public Property findById(final UUID id) {
        final var property = adapter.findById(id);
        if (property == null) throw new ResourceNotFoundException("Property not found for the given ID");

        return property;
    }

    public Property createProperty(final Property property) {
        return adapter.create(property);
    }
}
