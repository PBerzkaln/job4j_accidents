package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentDataRepository extends CrudRepository<Accident, Integer> {
    @Query("SELECT DISTINCT a FROM Accident AS a JOIN FETCH a.type JOIN FETCH a.rules")
    List<Accident> findAll();

    @Query("FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules WHERE a.id = :ID")
    Optional<Accident> findById(@Param("ID") int accidentId);
}