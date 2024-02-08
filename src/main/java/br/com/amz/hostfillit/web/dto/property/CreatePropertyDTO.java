package br.com.amz.hostfillit.web.dto.property;

import br.com.amz.hostfillit.usecases.domain.Property;
import org.springframework.util.Assert;

public record CreatePropertyDTO(String name, String location) {

    public void validate() {
        Assert.hasText(name, "Name must be provided");
        Assert.hasText(location, "Location must be provided");
    }

    public Property toModel() {
        return new Property(null, name, location);
    }
}
