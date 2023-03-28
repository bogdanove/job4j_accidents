CREATE TABLE accidents_rules (
                              id serial PRIMARY KEY,
                              acc_id int not null REFERENCES accidents(id),
                              rul_id int not null REFERENCES rules(id)
);