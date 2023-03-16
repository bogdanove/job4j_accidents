package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    private AtomicInteger accId = new AtomicInteger();

    private Map<Integer, AccidentType> accidentTypes = new ConcurrentHashMap<>();

    private Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    public Collection<Accident> getAllAccidents() {
        return accidents.values();
    }

    public Collection<AccidentType> getAllAccidentTypes() {
        return accidentTypes.values();
    }

    public Collection<Rule> getAllRules() {
        return rules.values();
    }

    public boolean add(Accident accident) {
        accident.setId(accId.incrementAndGet());
        return accidents.put(accident.getId(), accident) == null;
    }

    public Accident findAccidentById(int id) {
        return accidents.get(id);
    }

    public AccidentType findAccidentTypeById(int id) {
        return accidentTypes.get(id);
    }

    public Rule findRuleById(int id) {
        return rules.get(id);
    }

    public boolean replace(Accident accident) {
        return accidents.replace(accident.getId(), accident) != null;
    }

    @PostConstruct
    public void fillStore() {
        var acc1 = new Accident(accId.incrementAndGet(), "SomeName1", "SomeDescription1", "SomeAddress1", null, null);
        var acc2 = new Accident(accId.incrementAndGet(), "SomeName2", "SomeDescription2", "SomeAddress2", null, null);
        var acc3 = new Accident(accId.incrementAndGet(), "SomeName3", "SomeDescription3", "SomeAddress3", null, null);
        accidents.put(acc1.getId(), acc1);
        accidents.put(acc2.getId(), acc2);
        accidents.put(acc3.getId(), acc3);
        var accT1 = new AccidentType(1, "Две машины");
        var accT2 = new AccidentType(2, "Машина и человек");
        var accT3 = new AccidentType(3, "Машина и велосипед");
        accidentTypes.put(accT1.getId(), accT1);
        accidentTypes.put(acc2.getId(), accT2);
        accidentTypes.put(accT3.getId(), accT3);
        var rul1 = new Rule(1, "Статья. 1");
        var rul2 = new Rule(2, "Статья. 2");
        var rul3 = new Rule(3, "Статья. 3");
        rules.put(rul1.getId(), rul1);
        rules.put(rul2.getId(), rul2);
        rules.put(rul3.getId(), rul3);
    }
}
