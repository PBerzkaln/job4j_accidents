package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemAccidentRepository implements AccidentRepository {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(0);

    public MemAccidentRepository() {
        save(new Accident(0, "БМВ-Мерседес", "удар при смене полосы",
                "Волоколамское ш. 25",
                new AccidentType(1, "Две машины"), Set.of(new Rule(1, "Статья. 1"))));
        save(new Accident(0, "Opel", "двойная штрафная",
                "Коломенский проезд 18",
                new AccidentType(2, "Машина и человек"), Set.of(new Rule(2, "Статья. 2"))));
    }

    @Override
    public Optional<Accident> save(Accident accident) {
        int id = nextId.incrementAndGet();
        accident.setId(id);
        accidents.put(accident.getId(), accident);
        return Optional.of(accident);
    }

    @Override
    public boolean deleteById(int accidentId) {
        return accidents.remove(accidentId) != null;
    }

    @Override
    public boolean update(Accident accident) {
        return accidents.computeIfPresent(accident.getId(), (id, oldAccident) ->
                new Accident(oldAccident.getId(), accident.getName(), accident.getText(),
                        accident.getAddress(), accident.getType(), accident.getRules())) != null;
    }

    @Override
    public Optional<Accident> findById(int accidentId) {
        return Optional.ofNullable(accidents.get(accidentId));
    }

    @Override
    public List<Accident> findAll() {
        return accidents.values().stream().toList();
    }
}