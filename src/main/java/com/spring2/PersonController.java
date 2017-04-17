package com.spring2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by cmitchell on 4/16/17.
 */
//created PersonController first and from controller created peopleRepository class
@Controller
public class PersonController {

    @Autowired
    PeopleRepository peopleRepository;

    @RequestMapping("/")
    //set the default value of the search arguement
    public String peoplelist(Model model, @RequestParam(defaultValue ="")String search){

        model.addAttribute("search", search);
        model.addAttribute("people", peopleRepository.listPeople(search));

        return "index";
    }

    @RequestMapping("/personForm")
    //add params to controller for personId and Model
    public String personForm(Model model, Integer personId) {

        if(personId ==null) {
            //add no arg constructor
            Person person = new Person();
            model.addAttribute("person", person);

        }else{
            model.addAttribute("person", peopleRepository.getPerson(personId));

        }
        return "personForm";
    }
    //what does PostMapping mean?
    @PostMapping("/savePerson")
    public String savePerson(Person person){


        peopleRepository.savePerson(person);
        return "redirect:/";
    }

}
