package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeHbnRepository implements AccidentTypeRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<AccidentType> findAll() {
        return crudRepository.query(
                "from AccidentType order by id asc", AccidentType.class);
    }

    @Override
    public Optional<AccidentType> findById(int accidentTypeId) {
        return crudRepository.optional(
                "from AccidentType where id = :fId", AccidentType.class,
                Map.of("fId", accidentTypeId)
        );
    }
}