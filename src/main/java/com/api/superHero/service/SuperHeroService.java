package com.api.superHero.service;

import com.api.superHero.entity.Superhero;
import com.api.superHero.exceptions.SuperHeroNotFoundException;

import java.util.List;

public interface SuperHeroService {

    List<Superhero> getAllSuperHeroes();

    Superhero getSuperHeroById(Long id);

    List<Superhero> getSuperHeroByNameContaining(String name);

    Superhero createSuperHero(Superhero superHeroDto);

    Superhero updateSuperHero(Long id, Superhero updatedSuperhero);

    void deleteSuperHeroById(Long id) throws SuperHeroNotFoundException;
}
