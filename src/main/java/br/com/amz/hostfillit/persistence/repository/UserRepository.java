package br.com.amz.hostfillit.persistence.repository;

import br.com.amz.hostfillit.persistence.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    Optional<UserEntity> findByMail(final String mail);
}
