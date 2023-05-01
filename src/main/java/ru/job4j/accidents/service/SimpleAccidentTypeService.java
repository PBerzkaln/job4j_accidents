package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeDataRepository;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.List;

@Service
@AllArgsConstructor
@ThreadSafe
public class SimpleAccidentTypeService implements AccidentTypeService {
    private final AccidentTypeDataRepository accidentTypeRepository;

    @Override
    public List<AccidentType> findAll() {
        return (List<AccidentType>) accidentTypeRepository.findAll();
    }
}