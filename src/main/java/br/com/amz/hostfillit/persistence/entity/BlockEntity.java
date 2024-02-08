package br.com.amz.hostfillit.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "block")
public class BlockEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private PropertyEntity property;
    private LocalDate startDate;
    private LocalDate endDate;

    public BlockEntity() {}

    public BlockEntity(final UUID id, final PropertyEntity property, final LocalDate startDate, final LocalDate endDate) {
        super(id);
        this.property = property;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public PropertyEntity getProperty() {
        return property;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}