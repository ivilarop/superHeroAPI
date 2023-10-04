package com.api.superHero.controller;

import com.api.superHero.entity.Superhero;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class SuperheroControllerIntegrationTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("SuperheroControllerIntegrationTest - Test return superHeroes list")
    @Test
    void whenListSuperHeroes_thenReturnStatusOkAndSuperheroList() throws Exception {
        mvc.perform(get("/api/v1/superHeroes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @DisplayName("SuperheroControllerIntegrationTest - Test return superHero by id")
    @Test
    void whenGetSuperHero_thenReturnStatusOkAndSuperHero() throws Exception {
        mvc.perform(get("/api/v1/superHeroes/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Spiderman"));
    }

    @DisplayName("SuperheroControllerIntegrationTest - Test for findById superHero return SuperHeroNotFoundException")
    @Test
    void givenSuperHeroIdDoesNotExist_whenGetSuperHero_thenReturnNotFoundException() throws Exception {
        mvc.perform(get("/api/v1/superHeroes/{id}", 5L))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("SuperheroControllerIntegrationTest - Test find SuperHeroes by name containing")
    @Test
    void whenListSuperHeroesByName_thenReturnStatusOkAndSuperheroList() throws Exception {
        mvc.perform(get("/api/v1/superHeroes/name/{name}", "man")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @DisplayName("SuperheroControllerIntegrationTest - Test for insert new superHero")
    @Test
    void whenCreateSuperHero_thenReturnStatusCreatedAndSuperHero() throws Exception {
        Superhero newSuperHero = Superhero.builder().name("Wonder Woman").build();
        mvc.perform(post("/api/v1/superHeroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSuperHero))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Wonder Woman"));
    }

    @DisplayName("SuperheroControllerIntegrationTest - Test for save superHero return already exist exception")
    @Test
    void givenSuperHeroExist_whenCreateSuperHero_thenReturnAlreadyExistException() throws Exception {
        Superhero newSuperHero = Superhero.builder().name("Spiderman").build();
        mvc.perform(post("/api/v1/superHeroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSuperHero))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @DisplayName("SuperheroControllerIntegrationTest - Test for update SuperHero")
    @Test
    void whenUpdateSuperHero_thenReturnStatusOkAndUpdatedSuperHero() throws Exception {
        Superhero updatedSuperHero = Superhero.builder().id(2L).name("IronMan").build();
        mvc.perform(put("/api/v1/superHeroes/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedSuperHero))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("IronMan"));
    }

    @DisplayName("SuperheroControllerIntegrationTest - Test for update superHero return SuperHeroNotFoundException")
    @Test
    void givenSuperHeroNotExist_whenUpdateSuperHero_thenReturnNotFoundException() throws Exception {
        Superhero updatedSuperHero = Superhero.builder().id(4L).name("IronMan").build();
        mvc.perform(put("/api/v1/superHeroes/{id}", 4L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedSuperHero))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("SuperheroControllerIntegrationTest - Test for delete superHero by id")
    @Test
    void whenDeleteSuperHero_thenReturnStatusOk_andSuperHeroIsDeleted() throws Exception {
        mvc.perform(delete("/api/v1/superHeroes/{id}", 3L)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());

        mvc.perform(get("/api/v1/superHeroes/{id}", 3L))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("SuperheroControllerIntegrationTest - Test for delete superHero return SuperHeroNotFoundException")
    @Test
    void givenSuperHeroNotExist_whenDeleteSuperHero_thenReturnNotFoundException() throws Exception {
        mvc.perform(delete("/api/v1/superHeroes/{id}", 4L)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
