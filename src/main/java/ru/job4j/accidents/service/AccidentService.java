package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.Collection;

@Service
@AllArgsConstructor
public class AccidentService {

    private AccidentMem store;

    public Collection<Accident> findAllAccidents() {
        return store.getAllAccidents();
    }
}
