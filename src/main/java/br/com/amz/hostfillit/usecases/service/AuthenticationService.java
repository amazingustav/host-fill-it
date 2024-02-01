package br.com.amz.hostfillit.usecases.service;

import br.com.amz.hostfillit.cryptography.service.CryptographyService;
import br.com.amz.hostfillit.usecases.adapter.AuthenticationAdapter;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final CryptographyService cryptographyService;
    private final AuthenticationAdapter adapter;

    public AuthenticationService(final CryptographyService cryptographyService,
                                 final AuthenticationAdapter adapter) {
        this.cryptographyService = cryptographyService;
        this.adapter = adapter;
    }

    public String signIn(final String username, final String password) {
        final var authentication = adapter.findActive(username);

        if (!cryptographyService.matches(password, authentication.password())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        return cryptographyService.generateToken(authentication.username());
    }
}
