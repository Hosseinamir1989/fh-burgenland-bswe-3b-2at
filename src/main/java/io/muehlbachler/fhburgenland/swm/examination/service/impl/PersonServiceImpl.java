package io.muehlbachler.fhburgenland.swm.examination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;
import io.muehlbachler.fhburgenland.swm.examination.model.Person;
import io.muehlbachler.fhburgenland.swm.examination.repository.PersonRepository;
import io.muehlbachler.fhburgenland.swm.examination.service.NoteService;
import io.muehlbachler.fhburgenland.swm.examination.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private NoteService noteService;

    /**
     * Retrieves all persons.
     * Purpose: To fetch all person entities from the database.
     * Output: List< Person> - A list of all persons.
     * Errors: Might throw DataAccessException if there are issues accessing the data.
     * */
    public List<Person> getAll() {
        return Lists.newArrayList(personRepository.findAll());
    }

    /**
     * Retrieves a person by their ID.
     * Purpose: To find and return a person if they exist in the database.
     * Input: `String` id of the person
     * Output: Optional< Person> - If exists, it is returned; otherwise, an empty Optional.
     * Errors: IllegalArgumentException may be thrown if 'id' is null.
     * */
    public Optional<Person> get(String id) {
        return personRepository.findById(id);
    }

    /**
     * Creates a new person in the database.
     * Purpose: To persist a new person in the database.
     * Input: 'firstName' (String), 'lastName' (String) - Names used for the search.
     * Output: person with potentially updated information
     * Errors: might throw exception if 'person' is null or has invalid fields.
     * */
    @Override
    public Person create(Person person) {
        return personRepository.save(person);
    }

    /**
     * Finds persons by their first or last name.
     * Purpose: To search for persons using their first or last name.
     * Input: 'firstName' (String), 'lastName' (String) - Names used for the search.
     * Output: List< Person> - A list of persons
     * Errors: for null or empty input parameters, potentially returning an empty list.
     * */

    @Override
    public List<Person> findByName(String firstName, String lastName) {
        if (firstName.isEmpty() && !lastName.isEmpty()) {
            return personRepository.findByFirstName(lastName);
        } else if (lastName.isEmpty() && !firstName.isEmpty()) {
            return personRepository.findByLastName(firstName);
        }
        return Lists.newArrayList();
    }

    /**
     * Creates a note for a specific person.
     * Purpose: To add a note to a person, identified by their ID.
     * Input: 'personId' (String) - The ID of the person, 'note' (Note) - The note to be added.
     * Output: Optional< Note> - wrapped in an Optional if the person exists.
     * Errors: If the personId does not exist cannot be created, an empty Optional is returned.
     */
    @Override
    public Optional<Note> createNote(String personId, Note note) {
        return get(personId).map((Person person) -> {
            note.setPerson(person);
            return noteService.create(note);
        });
    }
}
