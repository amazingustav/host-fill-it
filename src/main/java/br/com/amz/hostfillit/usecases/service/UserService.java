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
        final var user = adapter.findById(id);
        if (user == null) throw new ResourceNotFoundException("User not found for the given ID");

        return user;
    }

    public User findOrCreateSignUp(final User user) {
        final var existenUser = adapter.findByMail(user.mail());

        if (existenUser != null) throw new IllegalArgumentException("User already exists");

        return adapter.create(user);
    }
}
