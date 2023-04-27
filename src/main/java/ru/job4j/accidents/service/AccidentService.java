package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AccidentService {
    List<Accident> findAll();

    Optional<Accident> create(Accident accident, int accidentTypeId, Set<Integer> rIds);

    Optional<Accident> findById(int accidentId);
    boolean update(Accident accident, Set<Integer> rIds);
    boolean delete(int accidentId);
}