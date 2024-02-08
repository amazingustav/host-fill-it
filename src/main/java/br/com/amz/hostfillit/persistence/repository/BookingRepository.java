package br.com.amz.hostfillit.persistence.repository;

import br.com.amz.hostfillit.persistence.entity.BookingEntity;
import br.com.amz.hostfillit.persistence.entity.PropertyEntity;
import java.util.UUID;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends CrudRepository<BookingEntity, UUID> {
    List<BookingEntity> findByPropertyIdAndIsActiveTrue(UUID propertyId);
}
