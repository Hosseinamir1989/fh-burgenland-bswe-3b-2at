package io.muehlbachler.fhburgenland.swm.examination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;
import io.muehlbachler.fhburgenland.swm.examination.repository.NoteRepository;
import io.muehlbachler.fhburgenland.swm.examination.service.NoteService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


/**
 * Service implementation for managing Note entities.
 * Performs CRUD operations and queries related to notes.
 * Ensures proper data access and manipulation.
 */

@NoArgsConstructor
@AllArgsConstructor
@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteRepository noteRepository;

    /**
     * Retrieves a note by its ID.
     * Purpose: To find and return a note if it exists in the database.
     * Input: 'id' (String) - The unique identifier of the note to be retrieved.
     * Output: If a note with the given ID exists, otherwise, an empty Optional is returned.
     * Errors:  If 'id' is null or not exist,an empty (null) Optional is returned.
     *
     * @param id The unique identifier of the note.
     * @return An Optional containing the requested note or empty if not found.
     * */
    @Override
    public Optional<Note> get(String id) {
        return noteRepository.findById(id);
    }

    /**
     * Creates and saves a new note in the database.
     * Purpose: To persist a new note in the database.
     * Input: 'note' (Note) - The note object to be saved.
     * Output: The saved note with updated information.
     * Errors: If object is null or invalid, the repository may throw an exception or in database.
     *
     * @param note The note object to be created.
     * @return The created note with updated information.
     * */
    @Override
    public Note create(Note note) {
        return noteRepository.save(note);
    }

    /**
     * Queries and returns notes based on content.
     * Purpose: To find and return a list of notes that contain the given query.
     * Input: 'query' (String) - The string used to search within the content of notes.
     * Output: 'List< Note>' - A list of notes that match the query.
     * Errors: needed handling for null or special character inputs to avoid unexpected behavior.
     *
     * @param query The note object to be created.
     * @return The founded query note with updated information.
     * */
    @Override
    public List<Note> queryByContent(String query) {
        return noteRepository.findByContentContaining(query);
    }
}
