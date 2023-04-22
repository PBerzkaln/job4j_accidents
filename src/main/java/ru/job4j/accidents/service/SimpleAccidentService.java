package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@ThreadSafe
public class SimpleAccidentService implements AccidentService {
    private final AccidentRepository accidentRepository;

    @Override
    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    @Override
    public Optional<Accident> create(Accident accident) {
        return accidentRepository.save(accident);
    }

    @Override
    public Optional<Accident> findById(int accidentId) {
        return accidentRepository.findById(accidentId);
    }

    @Override
    public boolean update(Accident accident) {
        return accidentRepository.update(accident);
    }
}