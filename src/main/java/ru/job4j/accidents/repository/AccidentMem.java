package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccidentMem {

    private Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public Collection<Accident> getAllAccidents() {
        return accidents.values();
    }

    @PostConstruct
    public void fillStore() {
        var acc1 = new Accident(1, "SomeName1", "SomeDescription1", "SomeAddress1");
        var acc2 = new Accident(2, "SomeName2", "SomeDescription2", "SomeAddress2");
        var acc3 = new Accident(3, "SomeName3", "SomeDescription3", "SomeAddress3");
        accidents.put(acc1.getId(), acc1);
        accidents.put(acc2.getId(), acc2);
        accidents.put(acc3.getId(), acc3);
    }
}
