package br.com.amz.hostfillit.cryptography.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CryptographyService {

	private final Algorithm algorithm;
	private final PasswordEncoder passwordEncoder;
	private final UserDetailsService userDetailsService;

    public CryptographyService(final PasswordEncoder passwordEncoder,
							   final UserDetailsService userDetailsService,
							   final @Value("${jwt.secret-key}") String secretKey) {
		this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
		this.algorithm = Algorithm.HMAC256(secretKey);
    }

	public String encrypt(final String input) {
		return this.passwordEncoder.encode(input);
	}

	public boolean matches(final String input, final String encoded) {
		return this.passwordEncoder.matches(input, encoded);
	}

	public String generateToken(final String username) {
		final var userDetails = this.userDetailsService.loadUserByUsername(username);
		final var expiresAt1Hour = new Date(System.currentTimeMillis() + 3600000);

		return JWT.create()
				.withSubject(userDetails.getUsername())
				.withExpiresAt(expiresAt1Hour)
				.sign(this.algorithm);
	}

	public Algorithm getAlgorithm() {
		return this.algorithm;
	}
}