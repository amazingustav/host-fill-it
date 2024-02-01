package br.com.amz.hostfillit.usecases.service;

import br.com.amz.hostfillit.usecases.adapter.AuthenticationAdapter;
import br.com.amz.hostfillit.usecases.domain.Authentication;
import br.com.amz.hostfillit.cryptography.service.CryptographyService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);
    private final CryptographyService cryptographyService;
    private final AuthenticationAdapter adapter;
    private final UserService userService;

    public AuthenticationService(final CryptographyService cryptographyService,
                                 final AuthenticationAdapter adapter,
                                 final UserService userService) {
        this.cryptographyService = cryptographyService;
        this.adapter = adapter;
        this.userService = userService;
    }

    public String signIn(final String username, final String password) {
        final var authentication = adapter.findActive(username);

        if (!cryptographyService.matches(password, authentication.password())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        return cryptographyService.generateToken(authentication.username());
    }

    @Transactional
    public String signUp(final Authentication authentication) {
        try {
            final var user = userService.findOrCreateSignUp(authentication.user());
            final var hashedPassword = cryptographyService.encrypt(authentication.password());

            final var validAuthentication = new Authentication(null,
                    authentication.username(), hashedPassword, true, user, null, null);

            adapter.create(validAuthentication);

            return cryptographyService.generateToken(authentication.username());
        } catch (Exception e) {
            LOGGER.error("Error while trying to sign up", e);
            throw e;
        }
    }
}
