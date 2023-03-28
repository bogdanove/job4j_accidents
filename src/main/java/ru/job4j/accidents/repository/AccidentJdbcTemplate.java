package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Set;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;

    private final static String INSERT_ACC = "insert into accidents (name, text, address, type_id) values (?, ?, ?, ?);";
    private final static String INSERT_ACC_RULES = "insert into accidents_rules(acc_id, rul_id) values (?, ?);";
    private final static String SELECT_ACC_BY_ID = "select id, name, text, address, type_id from accidents where id=?;";
    private final static String SELECT_ACC = "select id, name, text, address, type_id from accidents;";
    private final static String SELECT_TYPE_BY_ID = "select id, name from types where id=?;";
    private final static String SELECT_RULE_BY_ID = "select id, name from rules where id=?;";
    private final static String SELECT_TYPES = "select id, name from types;";
    private final static String SELECT_RULES = "select id, name from rules;";
    private final static String UPDATE_ACC = "update accidents set name=?, text=?, address=? where id=?;";

    public boolean save(Accident accident) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(cn -> {
            PreparedStatement ps = cn.prepareStatement(INSERT_ACC, new String[]{"id"});
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, keyHolder);
        accident.getRules().forEach(it -> jdbc.update(INSERT_ACC_RULES, keyHolder.getKey(), it.getId()));
        return accident != null;
    }

    public Accident findAccidentById(int id) {
        return jdbc.queryForObject(SELECT_ACC_BY_ID,
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    var type = findAccidentTypeById(rs.getInt("type_id"));
                    accident.setType(type);
                    return accident;
                }, id);
    }

    public boolean replace(Accident accident) {
        return jdbc.update(
                UPDATE_ACC, accident.getName(), accident.getText(), accident.getAddress(), accident.getId()
        ) > 0;
    }

    public List<Accident> getAll() {
        return jdbc.query(SELECT_ACC,
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    var type = findAccidentTypeById(rs.getInt("type_id"));
                    accident.setType(type);
                    return accident;
                });
    }

    public List<AccidentType> getAllAccidentTypes() {
        return jdbc.query(SELECT_TYPES,
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("name"));
                    return accidentType;
                });
    }

    public Set<Rule> getAllRules() {
        return Set.copyOf(jdbc.query(SELECT_RULES,
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                }));
    }

    public AccidentType findAccidentTypeById(int id) {
        return jdbc.queryForObject(SELECT_TYPE_BY_ID, (rs, row) -> {
                    var newType = new AccidentType();
                    newType.setId(rs.getInt("id"));
                    newType.setName(rs.getString("name"));
                    return newType;
                },
                id);
    }

    public Rule findRuleById(int id) {
        return jdbc.queryForObject(SELECT_RULE_BY_ID, (rs, row) -> {
                    var rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                },
                id);
    }
}
