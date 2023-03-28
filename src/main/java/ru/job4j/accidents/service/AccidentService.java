package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentJdbcTemplate;
import ru.job4j.accidents.repository.AccidentMem;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;

@Service
@AllArgsConstructor
public class AccidentService {

    private AccidentJdbcTemplate template;

    public Collection<Accident> findAllAccidents() {
        return template.getAll();
    }

    public Collection<AccidentType> findAllAccidentTypes() {
        return template.getAllAccidentTypes();
    }

    public Collection<Rule> findAllRules() {
        return template.getAllRules();
    }

    public boolean add(Accident accident, HttpServletRequest req) {
        accident.setType(template.findAccidentTypeById(accident.getType().getId()));
        Arrays.stream(req.getParameterValues("rIds")).forEach(s -> accident.getRules().add(template.findRuleById(Integer.parseInt(s))));
        return template.save(accident);
    }

    public Accident findById(int id) {
        return template.findAccidentById(id);
    }

    public AccidentType findAccidentTypeById(int id) {
        return template.findAccidentTypeById(id);
    }

    public Rule findRuleById(int id) {
        return template.findRuleById(id);
    }

    public boolean replace(Accident accident) {
        return template.replace(accident);
    }
}
