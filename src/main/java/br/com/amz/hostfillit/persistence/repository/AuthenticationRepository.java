package br.com.amz.hostfillit.persistence.repository;

import br.com.amz.hostfillit.persistence.entity.AuthenticationEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends CrudRepository<AuthenticationEntity, UUID> {
    Optional<AuthenticationEntity> findByUsernameAndActiveIsTrue(final String username);
}
