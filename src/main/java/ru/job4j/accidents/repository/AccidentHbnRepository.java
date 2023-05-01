package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class AccidentHbnRepository implements AccidentRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Accident> save(Accident accident) {
        crudRepository.run((session -> session.save(accident)));
        return Optional.of(accident);
    }

    @Override
    public boolean deleteById(int accidentId) {
        return crudRepository.booleanQuery("DELETE Accident WHERE id = :fId", Map.of("fId", accidentId));
    }

    @Override
    public boolean update(Accident accident) {
        crudRepository.run(session -> session.merge(accident));
        return true;
    }

    @Override
    public Optional<Accident> findById(int accidentId) {
        return crudRepository.optional("FROM Accident AS a JOIN FETCH a.type "
                + "JOIN FETCH a.rules WHERE a.id = :fId", Accident.class, Map.of("fId", accidentId));
    }

    @Override
    public List<Accident> findAll() {
        return crudRepository.query("SELECT DISTINCT a FROM Accident AS a JOIN FETCH a.type AS ty "
                + "JOIN FETCH a.rules AS ru ORDER BY a.id ASC", Accident.class);
    }
}