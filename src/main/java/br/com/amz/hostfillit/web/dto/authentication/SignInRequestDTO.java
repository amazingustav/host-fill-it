package br.com.amz.hostfillit.web.dto.authentication;

import org.springframework.util.Assert;

public record SignInRequestDTO(String username, String password) {
    public void validate() {
        Assert.hasText(username, "Username must be provided");
        Assert.hasText(password, "Password must be provided");
    }
}

