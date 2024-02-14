package io.muehlbachler.fhburgenland.swm.examination.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import io.muehlbachler.fhburgenland.swm.examination.model.Person;
import io.muehlbachler.fhburgenland.swm.examination.repository.PersonRepository;
import io.muehlbachler.fhburgenland.swm.examination.service.NoteService;
import io.muehlbachler.fhburgenland.swm.examination.service.PersonService;

/**
 * Tests for PersonServiceImpl, covering CRUD operations, note management, and searches. Utilizes
 * Mockito for mocking repository and service layers to ensure functionality correctness without
 * external dependencies. Methods test scenarios including successful entity retrieval, handling of
 * non-existent entities, and entity creation, showcasing the use of JUnit and Mockito in service
 * layer testing.
 */

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {
    @Mock
    private NoteService noteService;
    @Mock
    private PersonRepository personRepository;

    private PersonService personService;

    @BeforeEach
    void setUp() {
        personService = new PersonServiceImpl(personRepository, noteService);
    }

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(personRepository);
        Mockito.verifyNoMoreInteractions(noteService);
    }

    @Test
    void testGetById() {
        when(personRepository.findById("1"))
                .thenReturn(Optional.of(new Person("1", "John",
                        "Doe", Lists.newArrayList())));

        Optional<Person> person = personService.get("1");

        assertTrue(person.isPresent());
        assertEquals("John", person.get().getFirstName(),
                "firstName should be John");

        Mockito.verify(personRepository,times(1)).findById("1");
    }


    @Test
    void testGetAll() {
        when(personRepository.findAll()).thenReturn(Arrays.asList(new Person("1",
                        "John", "Doe", Collections.emptyList()),
                new Person("2", "Jane", "Doe", Collections.emptyList())));

        List<Person> persons = personService.getAll();

        assertNotNull(persons);
        assertEquals(2, persons.size());
        verify(personRepository).findAll();
    }

    @Test
    void testGetByIdNotFound() {
        when(personRepository.findById("nonexistent")).thenReturn(Optional.empty());

        Optional<Person> person = personService.get("nonexistent");

        assertFalse(person.isPresent());
        verify(personRepository).findById("nonexistent");
    }

    @Test
    void testFindByName() {
        when(personRepository.findByFirstNameAndLastName("John", "Doe"))
                .thenReturn(Collections.singletonList(new Person(
                        "1", "John", "Doe", Collections.emptyList())));

        List<Person> persons = personService.findByName("John", "Doe");

        assertNotNull(persons);
        assertEquals(1, persons.size());
        assertEquals("John", persons.get(0).getFirstName());
        verify(personRepository).findByFirstNameAndLastName("John", "Doe");
    }

    @Test
    void testCreatePerson() {
        Person newPerson = new Person(null,
                "New", "Person", Collections.emptyList());
        Person savedPerson = new Person("1",
                "New", "Person", Collections.emptyList());
        when(personRepository.save(any(Person.class))).thenReturn(savedPerson);

        Person result = personService.create(newPerson);

        assertNotNull(result);
        assertEquals("New", result.getFirstName());
        verify(personRepository).save(newPerson);
    }

 @Test
    void findByName_withNullParameters() {
        List<Person> result = personService.findByName(null, null);

        assertTrue(result.isEmpty(),
         "Expected no persons to be found when querying with null parameters");
    }

    @Test
    void testCreateNoteSuccess() {
        String personId = "1";
        Note newNote = new Note();
        newNote.setContent("Test Note");
        Person person = new Person(personId,
                "John", "Doe", Collections.emptyList());

        when(personRepository.findById(anyString())).thenReturn(Optional.of(person));
        when(noteService.create(any(Note.class))).thenReturn(newNote);

        Optional<Note> createdNote = personService.createNote(personId, newNote);

        assertTrue(createdNote.isPresent());
        assertEquals("Test Note", createdNote.get().getContent());
        verify(personRepository).findById(personId);
        verify(noteService).create(newNote);
    }

    @Test
    void testCreateNoteForNonExistentPerson() {
        String personId = "nonexistent";
        Note newNote = new Note();

        when(personRepository.findById(anyString())).thenReturn(Optional.empty());

        Optional<Note> createdNote = personService.createNote(personId, newNote);

        assertFalse(createdNote.isPresent());
        verify(personRepository).findById(personId);
        verify(noteService, never()).create(any(Note.class));
    }

    @Test
    void findByName_whenNoPersonsMatch() {
        lenient().when(personRepository.findByFirstName(anyString()))
                .thenReturn(Lists.newArrayList());
        lenient().when(personRepository.findByLastName(anyString()))
                .thenReturn(Lists.newArrayList());
        lenient().when(personRepository.findByFirstNameAndLastName(anyString(), anyString()))
                .thenReturn(Lists.newArrayList());
        when(personRepository.findByFirstNameAndLastName(anyString(), anyString()))
                .thenReturn(Lists.newArrayList());

        List<Person> result = personService.findByName("NonExistingFirstName",
                "NonExistingLastName");

        assertTrue(result.isEmpty(), "Expected no persons to be found with a non-matching name");
    }
}
