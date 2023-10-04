package com.api.superHero.controller;

import com.api.superHero.entity.Superhero;
import com.api.superHero.service.JwtService;
import com.api.superHero.service.SuperHeroService;
import com.api.superHero.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers =  SuperHeroController.class,
        excludeAutoConfiguration = {
                UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class
        }
)
public class SuperheroControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SuperHeroService superHeroService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserService userService;
    @Autowired
    private  ObjectMapper objectMapper;
    Superhero superhero1;
    Superhero superhero2;

    @BeforeEach
    void setUp() {
        superhero1 = Superhero.builder().id(1L).name("Spiderman").build();
        superhero2 = Superhero.builder().id(2L).name("Superman").build();
    }

    @AfterEach
    void tearDown() {
        superhero1 = superhero2 = null;
    }

    @DisplayName("SuperHeroControllerTest - Test return superHeroes list")
    @Test
    void should_return_superHero_list() throws Exception {
        when(superHeroService.getAllSuperHeroes()).thenReturn(Arrays.asList(superhero1,superhero2));
        mockMvc.perform(get("/api/v1/superHeroes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$").isArray());
    }

    @DisplayName("SuperHeroControllerTest - Test return superHero by id")
    @Test
    void should_return_superHero() throws Exception {
        when(superHeroService.getSuperHeroById(1L)).thenReturn(superhero1);
        mockMvc.perform(get("/api/v1/superHeroes/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(superhero1.getId().intValue())))
                .andExpect(jsonPath("$.name", is(superhero1.getName())))
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @DisplayName("SuperHeroControllerTest - Test find SuperHeroes by name containing")
    @Test
    void findByNameContaining_should_superHero_list() throws Exception {
        when(superHeroService.getSuperHeroByNameContaining("man")).thenReturn(Arrays.asList(superhero1,superhero2));
        mockMvc.perform(get("/api/v1/superHeroes/name/man"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$").isArray());
    }

    @DisplayName("SuperHeroControllerTest - Test for insert new superHero")
    @Test
    void should_add_new_superhero() throws Exception {
        when(superHeroService.createSuperHero(any(Superhero.class))).thenReturn(superhero1);
        mockMvc.perform(post("/api/v1/superHeroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(superhero1))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(superhero1.getId().intValue())))
                .andExpect(jsonPath("$.name", is(superhero1.getName())))
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @DisplayName("SuperHeroControllerTest - Test for update SuperHero")
    @Test
    void should_update_existing_superhero() throws Exception {
        when(superHeroService.updateSuperHero(any(Long.class), any(Superhero.class)))
                .thenReturn(Superhero.builder().id(2L).name("Hulk").build());
        mockMvc.perform(put("/api/v1/superHeroes/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(Superhero.builder().name("Hulk").build()))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(superhero2.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Hulk")))
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @DisplayName("SuperHeroControllerTest - Test for delete superHero by id")
    @Test
    void should_remove_superhero() throws Exception {
        mockMvc.perform(delete("/api/v1/superHeroes/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
