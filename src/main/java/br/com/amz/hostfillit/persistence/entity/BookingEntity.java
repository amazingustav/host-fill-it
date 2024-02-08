package br.com.amz.hostfillit.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "booking")
public class BookingEntity extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity guest;
    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private PropertyEntity property;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive;

    public BookingEntity() {}

    public BookingEntity(UUID id, UserEntity guest, PropertyEntity property, LocalDate startDate,
                         LocalDate endDate, boolean isActive) {
        super(id);
        this.guest = guest;
        this.property = property;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
    }

    public UserEntity getGuest() {
        return guest;
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

    public boolean isActive() {
        return isActive;
    }
}