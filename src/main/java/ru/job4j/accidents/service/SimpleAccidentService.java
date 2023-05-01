package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@ThreadSafe
public class SimpleAccidentService implements AccidentService {
    private final AccidentDataRepository accidentRepository;
    private final AccidentTypeDataRepository accidentTypeRepository;
    private final RuleDataRepository ruleRepository;

    @Override
    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    @Override
    public Optional<Accident> create(Accident accident, int accidentTypeId, Set<Integer> rIds) {
        Set<Rule> rulesToAdd = ruleSetHandler(rIds);
        accident.setType(accidentTypeRepository.findById(accidentTypeId).get());
        accident.setRules(rulesToAdd);
        return Optional.of(accidentRepository.save(accident));
    }

    @Override
    public Optional<Accident> findById(int accidentId) {
        return accidentRepository.findById(accidentId);
    }

    @Override
    public boolean update(Accident accident, Set<Integer> rIds) {
        Set<Rule> rulesToAdd = ruleSetHandler(rIds);
        accident.setRules(rulesToAdd);
        var type = accidentTypeRepository.findById(accident.getType().getId()).get();
        accident.setType(type);
        accidentRepository.save(accident);
        return true;
    }

    @Override
    public boolean delete(int accidentId) {
        accidentRepository.deleteById(accidentId);
        return true;
    }

    private Set<Rule> ruleSetHandler(Set<Integer> rIds) {
        return rIds.stream().map(ruleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}