package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.List;

@Service
@AllArgsConstructor
@ThreadSafe
public class SimpleRuleService implements RuleService {
    private final RuleRepository ruleRepository;

    @Override
    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }
}