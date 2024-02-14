package io.muehlbachler.fhburgenland.swm.examination.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.muehlbachler.fhburgenland.swm.examination.model.Person;

/**
 * Repository interface for managing Person entities.
 * Provides CRUD operations and custom query methods for persons.
 * Ensures proper data access and manipulation.
 */

public interface PersonRepository extends CrudRepository<Person, String> {

    /**
     * Finds persons by their first name.
     * Purpose: To retrieve a list of persons with a specific first name.
     * Input: 'firstName' (String) - The first name to search for.
     * Output: A list of persons with the specified first name.
     * Errors: Might throw exceptions related to database access issues or invalid query syntax.
     *
     * @param firstName The first name to search for.
     * @return A list of persons with the specified first name.
     */
    List<Person> findByFirstName(String firstName);

    /**
     * Finds persons by their last name.
     * Purpose: To retrieve a list of persons with a specific last name.
     * Input: 'lastName' (String) - The last name to search for.
     * Output: A list of persons with the specified last name.
     * Errors: Might throw exceptions related to database access issues or invalid query syntax.
     *
     * @param lastName The last name to search for.
     * @return A list of persons with the specified last name.
     */
    List<Person> findByLastName(String lastName);

    /**
     * Finds persons by both first and last name.
     * Purpose: To retrieve a list of persons with a specific combination of first and last name.
     * Input: 'firstName' (String), 'lastName' (String) - The first and last names to search for.
     * Output: A list of persons with the specified combination of first and last names.
     * Errors: Might throw exceptions related to database access issues or invalid query syntax.
     *
     * @param firstName The first name to search for.
     * @param lastName The last name to search for.
     * @return A list of persons matching both the specified first and last names.
     */
    List<Person> findByFirstNameAndLastName(String firstName, String lastName);
}
