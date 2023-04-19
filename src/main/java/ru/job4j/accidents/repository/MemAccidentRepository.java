package ru.job4j.accidents.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class MemAccidentRepository implements AccidentRepository {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(0);

    public MemAccidentRepository() {
        save(new Accident(0, "БМВ-Мерседес", "удар при смене полосы",
                "Волоколамское ш. 25"));
        save(new Accident(0, "Opel", "двойная штрафная",
                "Коломенский проезд 18"));
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
                        accident.getAddress())) != null;
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