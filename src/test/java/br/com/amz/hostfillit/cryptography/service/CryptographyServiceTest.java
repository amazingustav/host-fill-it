package br.com.amz.hostfillit.cryptography.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CryptographyServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserDetailsService userDetailsService;

    private CryptographyService cryptographyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        final var secretKey = "secret";
        cryptographyService = new CryptographyService(passwordEncoder, userDetailsService, secretKey);
    }

    @Test
    void encrypt_ShouldCallPasswordEncoder() {
        final var input = "password";
        cryptographyService.encrypt(input);

        verify(passwordEncoder).encode(input);
    }

    @Test
    void matches_ShouldCallPasswordEncoder() {
        final var input = "password";
        final var encodedPassword = "encodedPassword";
        cryptographyService.matches(input, encodedPassword);

        verify(passwordEncoder).matches(input, encodedPassword);
    }

    @Test
    void generateToken_ShouldReturnToken() {
        final var username = "user";
        final var userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(username);

        final var token = cryptographyService.generateToken(username);
        assertNotNull(token);
    }
}
