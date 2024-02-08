package br.com.amz.hostfillit.persistence.repository;

import br.com.amz.hostfillit.persistence.entity.PropertyEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends CrudRepository<PropertyEntity, UUID> {}
