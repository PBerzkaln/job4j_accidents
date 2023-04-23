package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@ThreadSafe
public class MemRuleRepository implements RuleRepository {
    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    public MemRuleRepository() {
        rules.put(1, new Rule(1, "Статья. 1"));
        rules.put(2, new Rule(2, "Статья. 2"));
        rules.put(3, new Rule(3, "Статья. 3"));
    }

    @Override
    public List<Rule> findAll() {
        return rules.values().stream().toList();
    }

    @Override
    public Optional<Rule> findById(int ruleId) {
        return Optional.ofNullable(rules.get(ruleId));
    }
}