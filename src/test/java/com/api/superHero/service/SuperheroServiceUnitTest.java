package com.api.superHero.service;

import com.api.superHero.entity.Superhero;
import com.api.superHero.exceptions.SuperHeroAlreadyExistException;
import com.api.superHero.exceptions.SuperHeroNotFoundException;
import com.api.superHero.repository.SuperHeroRepository;
import com.api.superHero.service.impl.SuperHeroServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuperheroServiceUnitTest {

    @Mock
    private SuperHeroRepository superHeroRepository;
    @InjectMocks
    private SuperHeroServiceImpl superHeroService;
    private Superhero superhero1;
    private Superhero superhero2;

    @BeforeEach
    void setUp() {
        superhero1 = Superhero.builder().id(1L).name("Spiderman").build();
        superhero2 = Superhero.builder().id(2L).name("Superman").build();
    }

    @AfterEach
    void tearDown() {
        superhero1 = superhero2 = null;
    }

    @DisplayName("SuperHeroServiceUnitTest - Test return superHeroes list")
    @Test
    void findAll_should_return_superHero_list() {
        when(superHeroRepository.findAllByOrderById()).thenReturn(Arrays.asList(superhero1, superhero2));
        List<Superhero> superheroesList = superHeroService.getAllSuperHeroes();
        assertEquals(2, superheroesList.size());
        verify(superHeroRepository).findAllByOrderById();
    }

    @DisplayName("SuperHeroServiceUnitTest - Test return superHero by id")
    @Test
    void findById_should_return_superHero() {
        when(superHeroRepository.findById(1L)).thenReturn(Optional.of(superhero1));
        Superhero returnedSuperhero = superHeroService.getSuperHeroById(1L);
        assertEquals(superhero1.getId(), returnedSuperhero.getId());
        assertEquals(superhero1.getName(), returnedSuperhero.getName());
        verify(superHeroRepository).findById(1L);
    }

    @DisplayName("SuperHeroServiceUnitTest - Test for findById superHero return SuperHeroNotFoundException")
    @Test
    void findById_no_should_return_superHero_thenExceptionIsThrown() {
        Superhero superHero = Superhero.builder().id(4L).name("Hulk").build();
        SuperHeroNotFoundException superHeroNotFoundException = assertThrows(SuperHeroNotFoundException.class,
                () -> superHeroService.getSuperHeroById(superHero.getId()));

        assertEquals("SuperHero Not Found with ID: " + superHero.getId(), superHeroNotFoundException.getMessage());
    }

    @DisplayName("SuperHeroServiceUnitTest - Test find SuperHeroes by name containing")
    @Test
    void findByNameContaining_should_superHero_list() {
        when(superHeroRepository.findByNameContainingIgnoreCaseOrderById("man"))
                .thenReturn(Arrays.asList(superhero1, superhero2));
        List<Superhero> superheroesList = superHeroService.getSuperHeroByNameContaining("man");
        assertEquals(2, superheroesList.size());
        verify(superHeroRepository).findByNameContainingIgnoreCaseOrderById("man");
    }

    @DisplayName("SuperHeroServiceUnitTest - Test for insert new superHero")
    @Test
    void save_should_insert_new_superHero() {
        superHeroService.createSuperHero(superhero2);
        verify(superHeroRepository, times(1)).save(superhero2);
        ArgumentCaptor<Superhero> superHeroArgumentCaptor = ArgumentCaptor.forClass(Superhero.class);
        verify(superHeroRepository).save(superHeroArgumentCaptor.capture());
        Superhero superheroCreated = superHeroArgumentCaptor.getValue();
        assertNotNull(superheroCreated.getId());
        assertEquals("Superman", superheroCreated.getName());
    }

    @DisplayName("SuperHeroServiceUnitTest - Test for save superHero return already exist exception")
    @Test
    void save_should_return_superHero_already_exist() {
        Superhero newSuperhero = Superhero.builder().id(4L).name("Spiderman").build();
        when(superHeroRepository.findByNameIgnoreCase(newSuperhero.getName())).thenReturn(Optional.of(superhero1));
        SuperHeroAlreadyExistException superHeroAlreadyExistException = assertThrows(SuperHeroAlreadyExistException.class,
                () -> superHeroService.createSuperHero(newSuperhero));

        assertEquals("Already exist superHero with given name:" + newSuperhero.getName(), superHeroAlreadyExistException.getMessage());
    }

    @DisplayName("SuperHeroServiceUnitTest - Test for update SuperHero")
    @Test
    void save_should_update_existing_superHero() {
        when(superHeroRepository.findById(1L)).thenReturn(Optional.of(superhero1));
        when(superHeroRepository.save(any(Superhero.class))).thenReturn(superhero1);
        superhero1.setName("SuperWoman");
        Superhero updatedSuperhero = superHeroService.updateSuperHero(1L, superhero1);
        assertEquals(updatedSuperhero.getName(), "SuperWoman");
    }

    @DisplayName("SuperHeroServiceUnitTest - Test for update superHero return SuperHeroNotFoundException")
    @Test
    void whenUpdateSuperHeroNotFound_thenExceptionIsThrown() {
        Superhero superHero = Superhero.builder().id(4L).name("Hulk").build();
        SuperHeroNotFoundException superHeroNotFoundException = assertThrows(SuperHeroNotFoundException.class,
                () -> superHeroService.updateSuperHero(superHero.getId(), superHero));

        assertEquals("SuperHero Not Found with ID: " + superHero.getId(), superHeroNotFoundException.getMessage());
    }

    @DisplayName("SuperHeroServiceUnitTest - Test for update superHero return IllegalArgumentException")
    @Test
    void whenUpdateSuperHeroIdNull_thenExceptionIsThrown() {
        Superhero superHero = Superhero.builder().id(null).name("Hulk").build();
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> superHeroService.updateSuperHero(superHero.getId(), superHero));

        assertEquals("SuperHero ID cannot be null", illegalArgumentException.getMessage());
    }

    @DisplayName("SuperHeroServiceUnitTest - Test for delete superHero by id")
    @Test
    void deleteById_should_delete_superHero() {
        when(superHeroRepository.findById(1L)).thenReturn(Optional.of(superhero1));
        superHeroService.deleteSuperHeroById(superhero1.getId());
        verify(superHeroRepository, times(1)).deleteById(superhero1.getId());
        ArgumentCaptor<Long> superHeroArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(superHeroRepository).deleteById(superHeroArgumentCaptor.capture());
        Long superHeroIdDeleted = superHeroArgumentCaptor.getValue();
        assertNotNull(superHeroIdDeleted);
        assertEquals(1L, superHeroIdDeleted);
    }

    @DisplayName("SuperHeroServiceUnitTest - Test for delete superHero return SuperHeroNotFoundException")
    @Test
    void whenDeleteSuperHeroNotFound_thenExceptionIsThrown() {
        Superhero superHero = Superhero.builder().id(4L).name("Hulk").build();
        SuperHeroNotFoundException superHeroNotFoundException = assertThrows(SuperHeroNotFoundException.class,
                () -> superHeroService.deleteSuperHeroById(superHero.getId()));

        assertEquals("SuperHero Not Found with ID: " + superHero.getId(), superHeroNotFoundException.getMessage());
    }
}