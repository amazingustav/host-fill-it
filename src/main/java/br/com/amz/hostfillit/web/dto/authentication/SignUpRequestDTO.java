package br.com.amz.hostfillit.web.dto.authentication;

import br.com.amz.hostfillit.usecases.domain.Authentication;
import br.com.amz.hostfillit.usecases.domain.User;
import org.springframework.util.Assert;

public record SignUpRequestDTO(String name, String mail, String username, String password) {

    public void validate() {
        Assert.hasText(name, "Name must be provided");
        Assert.hasText(mail, "Mail must be provided");
        Assert.hasText(username, "Username must be provided");
        Assert.hasText(password, "Password must be provided");
    }

    public Authentication toModel() {
        final var user = new User(null, name, mail);

        return new Authentication(null, username, password, true, user);
    }
}

