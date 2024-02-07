package br.com.amz.hostfillit.usecases.adapter;

import br.com.amz.hostfillit.persistence.entity.AuthenticationEntity;
import br.com.amz.hostfillit.persistence.repository.AuthenticationRepository;
import br.com.amz.hostfillit.usecases.exception.ResourceNotFoundException;
import br.com.amz.hostfillit.utils.AuthenticationMock;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthenticationAdapterTest {
    @Mock
    private AuthenticationRepository repository;

    private AuthenticationAdapter adapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        adapter = new AuthenticationAdapter(repository);
    }

    @Test
    public void findActive_WhenUserExists_ReturnsAuthentication() throws ResourceNotFoundException {
        // Given
        final var username = "testUser";
        final var entity = new AuthenticationMock().username(username).build().toEntity();
        when(repository.findByUsernameAndIsActiveTrue(username)).thenReturn(Optional.of(entity));

        // When
        final var result = adapter.findActive(username);

        // Then
        assertNotNull(result);
        verify(repository).findByUsernameAndIsActiveTrue(username);
    }

    @Test
    public void findActive_WhenUserNotExists_ThrowsResourceNotFoundException() {
        // Given
        final var username = "nonExistingUser";
        when(repository.findByUsernameAndIsActiveTrue(username)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> adapter.findActive(username));
    }

    @Test
    public void create_SavesAndReturnsAuthentication() {
        // Given
        final var authentication = new AuthenticationMock().build();
        final var entity = authentication.toEntity();
        when(repository.save(any(AuthenticationEntity.class))).thenReturn(entity);

        // When
        final var result = adapter.create(authentication);

        // Then
        assertEquals(result.username(), "johnDoe");
        assertEquals(result.user().name(), "John Doe");
        verify(repository).save(any(AuthenticationEntity.class));
    }
}
