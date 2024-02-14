package io.muehlbachler.fhburgenland.swm.examination.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;


/**
 * Manages database operations for notes.
 * Provides methods for CRUD operations and custom queries.
 * Ensures proper handling of database interactions.
 */

public interface NoteRepository extends CrudRepository<Note, String> {

    /**
     * Finds notes whose content contains the specified string.
     * Purpose: To retrieve a list of notes that contain a given string in their content.
     * Input: 'content' (String) - The text to search for within the notes' content.
     * Output: List< Note> - A list of notes where the content contains the specified string.
     * Errors: might throw exceptions related to invalid query syntax.
     *
     * @param content The string to search for within the notes' content.
     * @return A list of notes that contain the specified string in their content.
     */
    List<Note> findByContentContaining(String content);
}
