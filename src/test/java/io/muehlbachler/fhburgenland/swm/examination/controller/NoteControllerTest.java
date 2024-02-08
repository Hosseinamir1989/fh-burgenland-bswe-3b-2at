package io.muehlbachler.fhburgenland.swm.examination.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.muehlbachler.fhburgenland.swm.examination.model.Note;
import io.muehlbachler.fhburgenland.swm.examination.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class NoteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NoteService noteService;

    @InjectMocks
    private NoteController noteController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
    }


    @Test
    void getNoteById_found() throws Exception {
        Optional<Note> note = Optional.of(new Note());
        when(noteService.get("1")).thenReturn(note);

        mockMvc.perform(get("/note/1"))
                .andExpect(status().isOk());
    }


    @Test
    void getNoteById_notFound() throws Exception {
        when(noteService.get("unknown")).thenReturn(Optional.empty());

        mockMvc.perform(get("/note/unknown"))
                .andExpect(status().isNotFound());
    }


    @Test
    void queryNotesByContent_found() throws Exception {
        when(noteService.queryByContent("test")).thenReturn(Arrays.asList(new Note(), new Note()));

        mockMvc.perform(get("/note/query").param("query", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }


    @Test
    void queryNotesByContent_notFound() throws Exception {
        when(noteService.queryByContent("nothing")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/note/query").param("query", "nothing"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }


    @Test
    void queryNotesByContent_emptyQuery() throws Exception {
        when(noteService.queryByContent("")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/note/query").param("query", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

    }


    @Test
    void queryNotesByContent_nullQuery() throws Exception {
        mockMvc.perform(get("/note/query"))
                .andExpect(status().isBadRequest());
    }


}