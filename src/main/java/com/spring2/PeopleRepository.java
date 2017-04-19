package com.spring2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/**
 * Created by cmitchell on 4/16/17.
 */

@Component
public class PeopleRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Person> listPeople(String search) {
        //add the like operator
        return jdbcTemplate.query("SELECT * FROM person WHERE lower(firstname) like lower(?) or lower(lastname) like lower(?) limit 100",
                //add the % to the query params
                new Object[]{"%" + search + "%", "%" + search + "%"},
                //mapper
                (resultSet, i) -> new Person(
                        resultSet.getInt("personId"),
                        resultSet.getString("title"),
                        resultSet.getString("firstname"),
                        resultSet.getString("middlename"),
                        resultSet.getString("lastname"),
                        resultSet.getString("suffix")
                ));

    }

    public Person getPerson(Integer personId) {
        return jdbcTemplate.queryForObject("SELECT * FROM person WHERE personid = ?",
                new Object[]{personId},
                (resultSet, i) -> new Person(
                        resultSet.getInt("personId"),
                        resultSet.getString("title"),
                        resultSet.getString("firstName"),
                        resultSet.getString("middleName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("suffix")


                ));
//    }
//
//    public void savePerson(Person person){
//        //return jdbcTemplate.queryForObject(
//
//        if(person.get)
    }

    public Person savePerson(Person person) {

        if (person.getPersonId() == null) {
            // this person doesn't exist
            // we need to insert them!
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(con -> {
                        PreparedStatement ps = con.prepareStatement(
                                "INSERT INTO person(title, firstname, middlename, lastname, suffix) " +
                                        "VALUES (?, ?, ?, ?, ?)", //  RETURN personId
                                Statement.RETURN_GENERATED_KEYS);

                        ps.setString(1, person.getTitle());
                        ps.setString(2, person.getFirstName());
                        ps.setString(3, person.getMiddleName());
                        ps.setString(4, person.getLastName());
                        ps.setString(5, person.getSuffix());

                        return ps;
                    },
                    keyHolder);


            person.setPersonId((Integer) keyHolder.getKeys().get("personid"));

        } else {
            // this person exists!
            // we need to update them!
            jdbcTemplate.update(
                    "UPDATE person " +
                            "SET " +
                            "title = ?, " +
                            "firstname = ?, " +
                            "middlename = ?, " +
                            "lastname = ?, " +
                            "suffix = ? " +
                            "WHERE personid = ?",
                    new Object[]{
                            person.getTitle(),
                            person.getFirstName(),
                            person.getMiddleName(),
                            person.getLastName(),
                            person.getSuffix(),
                            person.getPersonId()
                    }
            );
        }


        return person;

    }

}
