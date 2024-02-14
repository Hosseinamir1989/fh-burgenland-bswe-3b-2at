package io.muehlbachler.fhburgenland.swm.examination.service;

import java.util.List;
import java.util.Optional;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;
import io.muehlbachler.fhburgenland.swm.examination.model.Person;

/**
 * Service interface for managing Person entities.
 * Defines methods for CRUD operations and querying persons.
 * Ensures abstraction and decoupling between the service layer and the data access layer.
 */

public interface PersonService {

    /**
     * Retrieves all persons.
     * Purpose: To fetch all person entities from the database.
     * Output: List< Person> - A list of all persons.
     * Errors: Underlying implementations may throw exceptions,in case of a data access issue.
     *
     * @return A list of all persons.
     */
    List<Person> getAll();

    /**
     * Retrieves a person by their ID.
     * Purpose: To find a specific person by their unique identifier.
     * Input: 'id' (String) - The unique identifier of the person.
     * Output: Optional<'Person'> - Contains the found person, or empty if no person is found.
     * Errors: Underlying implementations might throw exceptions, for example, if 'id' is null.
     *
     * @param id The unique identifier of the person.
     * @return An Optional containing the person, if found.
     */
    Optional<Person> get(String id);

    /**
     * Creates and saves a new person.
     * Purpose: To add a new person to the database.
     * Input: 'person' (Person) - The person object to be saved.
     * Output: Person - The saved person
     * Errors: Implementations might throw exceptions if the person object is invalid or null.
     *
     * @param person The person object to be created and saved.
     * @return The saved person.
     */
    Person create(Person person);

    /**
     * Finds persons by their first or last name.
     * Purpose: If any name (first or last) is empty, only the other one is searched for.
     * Input: 'firstName' (String), 'lastName' (String) - Names used for the search.
     * Output: List< Person> - A list of persons that match the search criteria.
     * Errors: for null or , potentially returning an empty list.
     *
     * @param firstName The first name to search for.
     * @param lastName The last name to search for.
     * @return A list of persons that match the name criteria.
     */


    List<Person> findByName(String firstName, String lastName);

    /**
     * Creates a note and associates it with a specific person.
     * Purpose: To add a note to a person, identified by their ID.
     * Input: 'personId' (String) - The ID of the person, 'note' (Note) - The note to be added.
     * Output: Optional<'Note'> - The created note, wrapped in an Optional if the person exists.
     * Errors: in the case of not existing an empty Optional is returned.
     *
     * @param personId The ID of the person to whom the note will be added.
     * @param note The note to be created and associated with the person.
     * @return An Optional containing the created note, if the person exists.
     */
    Optional<Note> createNote(String personId, Note note);
}
