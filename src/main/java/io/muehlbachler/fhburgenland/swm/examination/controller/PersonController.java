package io.muehlbachler.fhburgenland.swm.examination.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;
import io.muehlbachler.fhburgenland.swm.examination.model.Person;
import io.muehlbachler.fhburgenland.swm.examination.service.PersonService;

@RestController
@RequestMapping("person")
public class PersonController {
    @Autowired
    private PersonService personService;


    /**
     * Lists all persons.
     * Purpose: To retrieve a list of all persons from the database.
     * Output: List< Person> - A list of all persons.
     * Errors: Might throw exceptions related to data access or network issues.
     *
     * @return A list of all persons.
     */
    @GetMapping("/")
    public List<Person> list() {
        return personService.getAll();
    }

    /**
     * Retrieves a person by their ID.
     * Purpose: To find a specific person by their unique identifier.
     * Input: 'id' (String) - The unique identifier of the person.
     * Output: ResponseEntity< Person>- found person, or an empty ResponseEntity if not found.
     * Errors: If 'id' is null or invalid, might result in a bad request error.
     *
     * @param id The unique identifier of the person.
     * @return A ResponseEntity containing the person, if found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Person> get(@PathVariable String id) {
        return ResponseEntity.of(personService.get(id));
    }

    /**
     * Creates a new person.
     * Purpose: To add a new person to the database.
     * Input: person( Person) - The person object to be created.
     * Output: Person - The newly created person.
     * Errors: If invalid, might throw a validation or data integrity exception.
     *
     * @param person The person object to be created.
     * @return The created person.
     */
    @PostMapping("/")
    public Person create(@RequestBody Person person) {
        return personService.create(person);
    }

    /**
     * Queries persons by first and/or last name.
     * Purpose: To find persons using their first or last names.
     * Input: 'firstName' (String), 'lastName' (String) - The names to search for.
     * Output: List< Person> - A list of persons that match the search criteria.
     * Errors: handling for null or empty input parameters, returning an empty list.
     *
     * @param firstName The first name to search for.
     * @param lastName The last name to search for.
     * @return A list of persons that match the name criteria.
     */
    @GetMapping("/query")
    public List<Person> query(@RequestParam("firstName") String firstName,
                              @RequestParam("lastName") String lastName) {
        return personService.findByName(firstName, lastName);
    }

    @PostMapping("/{id}/note")
    public ResponseEntity<Note> createNote(@PathVariable String id, @RequestBody Note note) {
        return ResponseEntity.of(personService.createNote(id, note));
    }
}
