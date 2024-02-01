package br.com.amz.hostfillit.web.config;

import br.com.amz.hostfillit.cryptography.service.CryptographyService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
	private static final String HEADER = "Authorization";
	private static final String PREFIX = "Bearer ";

	@Lazy
	private final CryptographyService cryptographyService;

	public JwtAuthorizationFilter(final CryptographyService cryptographyService) {
		this.cryptographyService = cryptographyService;
	}

	@Override
	protected void doFilterInternal(final HttpServletRequest request,
									final HttpServletResponse response,
									final FilterChain filterChain) throws IOException {
		try {
			if (this.validateTokenHeader(request)) {
				final var accessToken = request.getHeader(HEADER).replace(PREFIX, "");
				final var decodedJWT = this.decodeToken(accessToken);

				this.configureAuthentication(decodedJWT);
            } else {
				SecurityContextHolder.clearContext();
			}

			filterChain.doFilter(request, response);
		} catch (Exception e) {
			final var message = "Error occurred while trying to validate the JWT token";

			logger.error(message, e);
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.sendError(HttpServletResponse.SC_FORBIDDEN, message);
		}
	}

	private boolean validateTokenHeader(HttpServletRequest request) {
		final var authenticationHeader = request.getHeader(HEADER);

		return authenticationHeader != null && authenticationHeader.startsWith(PREFIX);
	}

	private DecodedJWT decodeToken(final String accessToken) {
		return JWT.require(this.cryptographyService.getAlgorithm())
				.build()
				.verify(accessToken);
	}

	private void configureAuthentication(DecodedJWT decodedJWT) {
		final var authoritiesClaim = decodedJWT.getClaim("authorities");

		if (authoritiesClaim.isNull()) {
			SecurityContextHolder.clearContext();
		} else {
			final var authorities = authoritiesClaim.asList(String.class);
			final var grantedAuthorities = authorities.stream()
					.map(SimpleGrantedAuthority::new)
					.toList();

			final var authenticationToken =
					new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, grantedAuthorities);

			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
	}
}
