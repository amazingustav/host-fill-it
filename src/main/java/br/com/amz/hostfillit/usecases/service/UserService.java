package br.com.amz.hostfillit.usecases.service;

import br.com.amz.hostfillit.usecases.adapter.UserAdapter;
import br.com.amz.hostfillit.usecases.exception.ResourceNotFoundException;
import br.com.amz.hostfillit.usecases.domain.User;
import java.util.UUID;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserAdapter adapter;

    public UserService(final UserAdapter adapter) {
        this.adapter = adapter;
    }

    public User findById(final UUID id) {
        return adapter.findById(id);
    }

    public User findOrCreateSignUp(final User user) {
        try {
            final var existenUser = adapter.findByMail(user.mail());

            if (existenUser != null) throw new IllegalArgumentException("User already exists");
        } catch (ResourceNotFoundException e) {
            return this.create(user);
        }

        return this.create(user);
    }

    public User create(final User user) {
        return adapter.create(user);
    }
}
