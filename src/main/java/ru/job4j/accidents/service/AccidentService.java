package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentMem;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;

@Service
@AllArgsConstructor
public class AccidentService {

    private AccidentMem store;

    public Collection<Accident> findAllAccidents() {
        return store.getAllAccidents();
    }

    public Collection<AccidentType> findAllAccidentTypes() {
        return store.getAllAccidentTypes();
    }

    public Collection<Rule> findAllRules() {
        return store.getAllRules();
    }

    public boolean add(Accident accident, HttpServletRequest req) {
        accident.setType(store.findAccidentTypeById(accident.getType().getId()));
        Arrays.stream(req.getParameterValues("rIds")).forEach(s -> accident.getRules().add(store.findRuleById(Integer.parseInt(s))));
        return store.add(accident);
    }

    public Accident findById(int id) {
        return store.findAccidentById(id);
    }

    public AccidentType findAccidentTypeById(int id) {
        return store.findAccidentTypeById(id);
    }

    public Rule findRuleById(int id) {
        return store.findRuleById(id);
    }

    public boolean replace(Accident accident) {
        return store.replace(accident);
    }
}
