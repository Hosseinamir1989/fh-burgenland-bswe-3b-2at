package io.muehlbachler.fhburgenland.swm.examination.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import io.muehlbachler.fhburgenland.swm.examination.model.Person;
import io.muehlbachler.fhburgenland.swm.examination.service.PersonService;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    void setup() {
        mockMvc = standaloneSetup(personController).build();
    }

    @Test
    void listPersons() throws Exception {
        when(personService.getAll()).thenReturn(Arrays.asList(new Person(), new Person()));

        mockMvc.perform(get("/person/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));

        verify(personService).getAll();
    }

    @Test
    void getAllPersons_success() throws Exception {
        // Assume that personService.getAll() has been defined to return a list of all persons.
        List<Person> allPersons = Arrays.asList(new Person(), new Person());
        when(personService.getAll()).thenReturn(allPersons);

        mockMvc.perform(get("/person/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(allPersons.size()));

        verify(personService).getAll();
    }

    @Test
    void createPerson() throws Exception {
        Person newPerson = new Person();
        newPerson.setFirstName("Jane");
        newPerson.setLastName("Doe");
        when(personService.create(any(Person.class))).thenReturn(newPerson);

        mockMvc.perform(post("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Jane\",\"lastName\":\"Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(personService).create(any(Person.class));
    }

    @Test
    void queryPersons() throws Exception {
        when(personService.findByName("John", "")).thenReturn(List.of(new Person()));

        mockMvc.perform(get("/person/query")
                        .param("firstName", "John")
                        .param("lastName", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));

        verify(personService).findByName("John", "");
    }

    @Test
    void createNoteForPerson() throws Exception {
        Note newNote = new Note();
        newNote.setContent("Test note content");
        when(personService.createNote(eq("1"), any(Note.class)))
                .thenReturn(Optional.of(newNote));

        mockMvc.perform(post("/person/1/note")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"Test note content\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Test note content"));

        verify(personService).createNote(eq("1"), any(Note.class));
    }

    @Test
    void listPersons_emptyList() throws Exception {
        when(personService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/person/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verify(personService).getAll();
    }

    @Test
    void queryPersons_emptyQueryParameters() throws Exception {
        when(personService.findByName("", "")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/person/query")
                        .param("firstName", "")
                        .param("lastName", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verify(personService).findByName("", "");
    }

    @Test
    void queryPersons_noMatchingPersons() throws Exception {
        when(personService.findByName("Nonexistent", "Person")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/person/query")
                        .param("firstName", "Nonexistent")
                        .param("lastName", "Person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verify(personService).findByName("Nonexistent", "Person");
    }

    @Test
    void createNoteForInvalidPerson() throws Exception {
        when(personService.createNote(eq("invalid"), any(Note.class))).thenReturn(Optional.empty());

        mockMvc.perform(post("/person/invalid/note")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"Test note content\"}"))
                .andExpect(status().isNotFound());

        verify(personService).createNote(eq("invalid"), any(Note.class));
    }

    @Test
    void createNoteForPerson_success() throws Exception {
        String personId = "1";
        Note newNote = new Note();
        newNote.setContent("This is a test note.");

        when(personService.createNote(eq(personId),
                any(Note.class))).thenReturn(Optional.of(newNote));

        mockMvc.perform(post("/person/{id}/note", personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"This is a test note.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(newNote.getContent()));

        verify(personService).createNote(eq(personId), any(Note.class));
    }
}
