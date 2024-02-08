package br.com.amz.hostfillit.usecases.service;

import br.com.amz.hostfillit.usecases.adapter.PropertyAdapter;
import br.com.amz.hostfillit.usecases.domain.Property;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    private final PropertyAdapter adapter;

    public PropertyService(final PropertyAdapter adapter) {
        this.adapter = adapter;
    }

    public Property findById(final UUID id) {
        return adapter.findById(id);
    }

    public Property createProperty(final Property property) {
        return adapter.create(property);
    }
}
