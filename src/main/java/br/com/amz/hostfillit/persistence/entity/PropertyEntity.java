package br.com.amz.hostfillit.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "property")
public class PropertyEntity extends AbstractEntity {
    private String name;
    private String location;

    public PropertyEntity() {}

    public PropertyEntity(final UUID id, final String name, final String location) {
        super(id);
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}
