package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.repository.AuthorityDataRepository;

@Service
@AllArgsConstructor
@ThreadSafe
public class SimpleAuthorityService implements AuthorityService {
    private final AuthorityDataRepository authorityDataRepository;

    @Override
    public Authority findByAuthority(String authority) {
        return authorityDataRepository.findByAuthority(authority);
    }
}