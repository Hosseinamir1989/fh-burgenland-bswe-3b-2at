package io.muehlbachler.fhburgenland.swm.examination.service.impl;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;
import io.muehlbachler.fhburgenland.swm.examination.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class NoteServiceImplTest {
    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteServiceImpl noteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetNoteByIdFound() {
        Note note = new Note("1", null, "Test Note");
        when(noteRepository.findById("1")).thenReturn(Optional.of(note));
        Optional<Note> foundNote = noteService.get("1");
        assertTrue(foundNote.isPresent());
        assertEquals("Test Note", foundNote.get().getContent());
    }

    @Test
    public void testGetNoteByIdNotFound() {
        when(noteRepository.findById("nonexistent"))
                .thenReturn(Optional.empty());
        Optional<Note> foundNote = noteService.get("nonexistent");
        assertFalse(foundNote.isPresent());
    }

    @Test
    public void testCreateNote() {
        Note newNote = new Note(null,
                null, "New Note");
        Note savedNote = new Note("1",
                null, "New Note");
        when(noteRepository.save(any(Note.class))).thenReturn(savedNote);
        Note result = noteService.create(newNote);
        assertNotNull(result);
        assertEquals("New Note", result.getContent());
    }

    @Test
    public void testQueryByContentFound() {
        List<Note> notes = List.of(new Note("1",
                null, "Note Content"));
        when(noteRepository.findByContentContaining("Content")).thenReturn(notes);
        List<Note> result = noteService.queryByContent("Content");
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Note Content",
                result.getFirst().getContent());
    }

    @Test
    public void testQueryByContentNotFound() {
        when(noteRepository.findByContentContaining("nonexistent")).thenReturn(List.of());
        List<Note> result = noteService.queryByContent("nonexistent");
        assertTrue(result.isEmpty());
    }


    @Test
    public void testGetNoteByNullId() {
        Optional<Note> foundNote = noteService.get(null);
        assertFalse(foundNote.isPresent(), "Expected an empty Optional for null ID");
    }

    @Test
    public void testCreateNoteWithNull() {

        try {
            Note result = noteService.create(null);
            assertNull(result, "Expected a null result when creating a note with null input");
        } catch (Exception e) {
            fail("Did not expect an exception to be thrown when creating a note with null input");
        }
    }

    @Test
    public void testQueryByContentWithNull() {
        try {
            var results = noteService.queryByContent(null);
            assertNotNull(results,
                    "Expected a non-null result for query by null content");
            assertTrue(results.isEmpty(),
                    "Expected an empty list when querying by null content");
        } catch (Exception e) {
            fail("Did not expect an exception to be thrown when querying by null content");
        }
    }
}