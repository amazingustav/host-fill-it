package br.com.amz.hostfillit.utils;

import br.com.amz.hostfillit.usecases.domain.User;
import java.util.UUID;

public class UserMock {

    private UUID id;
    private String name;
    private String mail;

    public UserMock() {
        this.id = UUID.randomUUID();
        this.name = "John Doe";
        this.mail = "john@doe.co";
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

    public User build() {
        return new User(id, name, mail);
    }
}
