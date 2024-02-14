package io.muehlbachler.fhburgenland.swm.examination.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;
import io.muehlbachler.fhburgenland.swm.examination.service.NoteService;

/**
 * Manages endpoints related to notes.
 * Retrieves specific notes and queries notes by content.
 * Ensures proper handling of input and output.
 * Avoids unexpected behavior due to invalid data.
 * Provides a RESTful interface for note-related operations.
 */

@RestController
@RequestMapping("note")
public class NoteController {
    @Autowired
    private NoteService noteService;

    /**
     * Retrieves a note by its ID.
     * Purpose: To find a specific note by its unique identifier.
     * Input: 'id' (String) - The unique identifier of the note.
     * Output: ResponseEntity< Note> - Contains the found note, or an empty if not found.
     * Errors: If 'id' is null or invalid, might result in a bad request error.
     *
     * @param id The unique identifier of the note.
     * @return A ResponseEntity containing the note, if found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Note> get(@PathVariable String id) {
        return ResponseEntity.of(noteService.get(id));
    }

    /**
     * Queries notes based on a specified content string.
     * Purpose: To retrieve a list of notes that contain a specified query string in their content.
     * Input: 'query' (String) - The text used to search in the content of notes.
     * Output: List< Note> - A list of notes containing the query string in their content.
     * Errors: handling needed for null or special character to avoid unexpected behavior.
     *
     * @param query The string to search for within the notes' content.
     * @return A list of notes that contain the query string.
     */
    @GetMapping("/query")
    public List<Note> query(@RequestParam("query") String query) {
        return noteService.queryByContent(query);
    }
}
