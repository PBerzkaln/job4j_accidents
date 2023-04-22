package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentService {
    List<Accident> findAll();

    Optional<Accident> create(Accident accident);

    Optional<Accident> findById(int accidentId);
    boolean update(Accident accident);
}