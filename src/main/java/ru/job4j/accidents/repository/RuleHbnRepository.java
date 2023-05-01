package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class RuleHbnRepository implements RuleRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<Rule> findAll() {
        return crudRepository.query(
                "from Rule order by id asc", Rule.class);
    }

    @Override
    public Optional<Rule> findById(int ruleId) {
        return crudRepository.optional(
                "from Rule where id = :fId", Rule.class,
                Map.of("fId", ruleId)
        );
    }
}