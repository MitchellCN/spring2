package com.spring2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by cmitchell on 4/19/17.
 */
@RestController
public class RestPeopleController {

    @Autowired
    PeopleRepository peopleRepository;

    @GetMapping("/api/people")
    public List<Person> listPeople(@RequestParam(defaultValue = "") String search) {
        return peopleRepository.listPeople(search);
    }

    @GetMapping("/api/person/{id}")
    public Person getPerson(@PathVariable("id")Integer id){
        return peopleRepository.getPerson(id);

    }

    @PostMapping("/api/person")
    public Person savePerson(@RequestBody Person person) {
       return peopleRepository.savePerson(person);



    }

}
