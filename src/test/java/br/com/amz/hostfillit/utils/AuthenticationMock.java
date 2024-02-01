package br.com.amz.hostfillit.utils;

import br.com.amz.hostfillit.usecases.domain.Authentication;
import br.com.amz.hostfillit.usecases.domain.User;
import java.time.Instant;
import java.util.UUID;

public class AuthenticationMock {

    private UUID id;
    private String username;
    private String password;
    private boolean isActive;
    private User user;
    private Instant createdAt;
    private Instant updatedAt;

    public AuthenticationMock() {
        this.id = UUID.randomUUID();
        this.username = "johnDoe";
        this.password = "password";
        this.user = new UserMock().build();
        this.isActive = true;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public AuthenticationMock id(UUID id) {
        this.id = id;
        return this;
    }

    public AuthenticationMock username(String username) {
        this.username = username;
        return this;
    }

    public AuthenticationMock password(String password) {
        this.password = password;
        return this;
    }

    public AuthenticationMock isActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public AuthenticationMock user(User user) {
        this.user = user;
        return this;
    }

    public AuthenticationMock createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public AuthenticationMock updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Authentication build() {
        return new Authentication(id, username, password, isActive, user, createdAt, updatedAt);
    }
}