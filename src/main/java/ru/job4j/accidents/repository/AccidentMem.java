package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccidentMem {

    private Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    private Map<Integer, AccidentType> accidentTypes = new ConcurrentHashMap<>();

    public Collection<Accident> getAllAccidents() {
        return accidents.values();
    }

    public Collection<AccidentType> getAllAccidentTypes() {
        return accidentTypes.values();
    }

    public boolean add(Accident accident) {
        accident.setId(accidents.size() + 1);
        return accidents.put(accident.getId(), accident) == null;
    }

    public Accident findAccidentById(int id) {
        return accidents.get(id);
    }

    public AccidentType findAccidentTypeById(int id) {
        return accidentTypes.get(id);
    }

    public boolean replace(Accident accident) {
        return accidents.replace(accident.getId(), accident) == null;
    }

    @PostConstruct
    public void fillStore() {
        var acc1 = new Accident(1, "SomeName1", "SomeDescription1", "SomeAddress1", null);
        var acc2 = new Accident(2, "SomeName2", "SomeDescription2", "SomeAddress2", null);
        var acc3 = new Accident(3, "SomeName3", "SomeDescription3", "SomeAddress3", null);
        accidents.put(acc1.getId(), acc1);
        accidents.put(acc2.getId(), acc2);
        accidents.put(acc3.getId(), acc3);
        var accT1 = new AccidentType(1, "Две машины");
        var accT2 = new AccidentType(2, "Машина и человек");
        var accT3 = new AccidentType(3, "Машина и велосипед");
        accidentTypes.put(accT1.getId(), accT1);
        accidentTypes.put(acc2.getId(), accT2);
        accidentTypes.put(accT3.getId(), accT3);

    }
}
