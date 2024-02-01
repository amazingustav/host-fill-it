package br.com.amz.hostfillit.utils;

import br.com.amz.hostfillit.usecases.domain.User;
import java.time.Instant;
import java.util.UUID;

public class UserMock {

    private UUID id;
    private String name;
    private String mail;
    private Instant createdAt;
    private Instant updatedAt;

    public UserMock() {
        this.id = UUID.randomUUID();
        this.name = "John Doe";
        this.mail = "john@doe.co";
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public UserMock id(UUID id) {
        this.id = id;
        return this;
    }

    public UserMock name(String name) {
        this.name = name;
        return this;
    }

    public UserMock mail(String mail) {
        this.mail = mail;
        return this;
    }

    public UserMock createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserMock updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public User build() {
        return new User(id, name, mail, createdAt, updatedAt);
    }
}
