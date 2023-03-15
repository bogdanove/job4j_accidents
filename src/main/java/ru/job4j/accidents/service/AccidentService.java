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

    public boolean add(Accident accident) {
        return store.add(accident);
    }

    public Accident findById(int id) {
        return store.findById(id);
    }

    public boolean replace(Accident accident) {
        return store.replace(accident);
    }
}
