package br.com.amz.hostfillit.usecases.adapter;

import br.com.amz.hostfillit.persistence.entity.UserEntity;
import br.com.amz.hostfillit.persistence.repository.UserRepository;
import br.com.amz.hostfillit.usecases.exception.ResourceNotFoundException;
import br.com.amz.hostfillit.utils.UserMock;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserAdapterTest {

    @Mock
    private UserRepository repository;

    private UserAdapter adapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        adapter = new UserAdapter(repository);
    }

    @Test
    public void findByMail_WhenUserExists_ReturnsUser() {
        // Given
        final var mail = "test@example.com";
        final var entity = new UserMock().mail(mail).build().toEntity();
        when(repository.findByMail(mail)).thenReturn(Optional.of(entity));

        // When
        final var result = adapter.findByMail(mail);

        // Then
        assertNotNull(result);
        assertEquals(mail, result.mail());
        verify(repository).findByMail(mail);
    }

    @Test
    public void findByMail_WhenUserNotExists_ThrowsResourceNotFoundException() {
        // Given
        final var mail = "missing@example.com";
        when(repository.findByMail(mail)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> adapter.findByMail(mail));
    }

    @Test
    public void create_CreatesAndReturnsUser() {
        // Given
        final var mockUser = new UserMock().mail("new@example.com").build();
        final var mockEntity = mockUser.toEntity();
        when(repository.save(any(UserEntity.class))).thenReturn(mockEntity);

        // When
        final var result = adapter.create(mockUser);

        // Then
        assertNotNull(result);
        verify(repository).save(any(UserEntity.class));
    }
}
