package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserDataRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
@ThreadSafe
public class SimpleUserService implements UserService {
    private final UserDataRepository userDataRepository;

    @Override
    public Optional<User> create(User user) {
        userDataRepository.save(user);
        return Optional.of(user);
    }
}