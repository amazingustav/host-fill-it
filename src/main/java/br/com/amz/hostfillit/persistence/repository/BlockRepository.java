package br.com.amz.hostfillit.persistence.repository;

import br.com.amz.hostfillit.persistence.entity.BlockEntity;
import br.com.amz.hostfillit.persistence.entity.BookingEntity;
import br.com.amz.hostfillit.persistence.entity.PropertyEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends CrudRepository<BlockEntity, UUID> {

    List<BlockEntity> findByPropertyIdAndIsActiveTrue(UUID propertyId);
}
