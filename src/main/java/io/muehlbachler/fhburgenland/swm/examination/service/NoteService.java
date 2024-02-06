package io.muehlbachler.fhburgenland.swm.examination.service;

import java.util.List;
import java.util.Optional;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;

public interface NoteService {

    /**
     * Retrieves a note by its ID.
     * Purpose: To find a specific note by its unique identifier.
     * Input: 'id' (String) - The unique identifier of the note to be retrieved.
     * Output: Optional< Note> -found note, or empty if no note is found.
     * Errors: may not directly throw exceptions.
     *
     * @param id The unique identifier of the note.
     * @return An Optional containing the note, if found.
     */
    Optional<Note> get(String id);

    /**
     * Creates and saves a new note.
     * Purpose: To add a new note to the database.
     * Input: 'note' (Note) - The note object to be saved.
     * Output: Note - The saved note, which may include auto-generated fields like ID.
     * Errors: might throw exceptions, such as if the note object is invalid or null.
     *
     * @param note The note object to be created and saved.
     * @return The saved note.
     */
    Note create(Note note);

    /**
     * Queries notes by matching content.
     * Purpose: To retrieve a list of notes that contain a specified query string in their content.
     * Input: 'query' (String) - The text used to search in the content of notes.
     * Output: List< Note> - A list of notes containing the query string in their content.
     * Errors: handle empty or null query differently and might throw exceptions on invalid queries.
     *
     * @param query The string to search for within the notes' content.
     * @return A list of notes that contain the query string.
     */
    List<Note> queryByContent(String query);
}
