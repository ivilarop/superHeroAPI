package com.api.superHero.repository;

import com.api.superHero.entity.Superhero;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SuperheroRepositoryUnitTest {

    @Autowired
    private SuperHeroRepository superHeroRepository;

    @DisplayName("SuperHeroRepositoryUnitTest - Test return superHeroes list")
    @Test
    void findAll_should_return_superHero_list() {
        List<Superhero> superheroList = superHeroRepository.findAllByOrderById();
        assertEquals(superheroList.size(),3);
    }

    @DisplayName("SuperHeroRepositoryUnitTest - Test return superHero by id")
    @Test
    void findById_should_return_superHero() {
        Optional<Superhero> superHero = superHeroRepository.findById(1L);
        assertTrue(superHero.isPresent());
    }

    @DisplayName("SuperHeroRepositoryUnitTest - Test no should return superHero by id")
    @Test
    void findById_no_should_return_superHero() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            superHeroRepository.findById(4L).get();
        });
        assertNotNull(exception);
        assertEquals(exception.getClass(),NoSuchElementException.class);
        assertEquals(exception.getMessage(),"No value present");
    }

    @DisplayName("SuperHeroRepositoryUnitTest - Test find SuperHeroes by name containing")
    @Test
    void findByNameContaining_should_return_superHero_list(){
        List<Superhero> superheroList = superHeroRepository.findByNameContainingIgnoreCaseOrderById("man");
        assertEquals(superheroList.size(), 3);
    }

    @DisplayName("SuperHeroRepositoryUnitTest - Test for insert new superHero")
    @Test
    void save_should_insert_new_superHero() {
        Superhero saved = Superhero.builder().build();
        saved.setName("Thor");
        Superhero returnedSuperhero = superHeroRepository.save(saved);
        assertNotNull(returnedSuperhero);
        assertEquals(4L, returnedSuperhero.getId());
        assertEquals(saved.getName(), returnedSuperhero.getName());
    }

    @DisplayName("SuperHeroRepositoryUnitTest - Test for update SuperHero")
    @Test
    void save_should_update_existing_superHero() {
        Optional<Superhero> existingSuperHero = superHeroRepository.findById(1L);
        assertTrue(existingSuperHero.isPresent());
        existingSuperHero.get().setName("Antman");
        Superhero updatedSuperhero = superHeroRepository.save(existingSuperHero.get());
        assertNotNull(updatedSuperhero);
        assertEquals("Antman", updatedSuperhero.getName());
    }

    @DisplayName("SuperHeroRepositoryUnitTest - Test for delete superHero by id")
    @Test
    void deleteById_should_delete_superHero() {
        superHeroRepository.deleteById(1L);
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            superHeroRepository.findById(1L).get();
        });
        assertNotNull(exception);
        assertEquals(exception.getClass(), NoSuchElementException.class);
        assertEquals(exception.getMessage(),"No value present");
    }
}
