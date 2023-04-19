package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentRepository {
    Optional<Accident> save(Accident accident);

    boolean deleteById(int accidentId);

    boolean update(Accident accident);

    Optional<Accident> findById(int accidentId);

    List<Accident> findAll();
}