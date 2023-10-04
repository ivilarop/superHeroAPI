package com.api.superHero.service.impl;

import com.api.superHero.entity.Superhero;
import com.api.superHero.exceptions.SuperHeroAlreadyExistException;
import com.api.superHero.exceptions.SuperHeroNotFoundException;
import com.api.superHero.repository.SuperHeroRepository;
import com.api.superHero.service.SuperHeroService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SuperHeroServiceImpl implements SuperHeroService {

    private final SuperHeroRepository superHeroRepository;

    @Override
    @Cacheable("superheroes")
    public List<Superhero> getAllSuperHeroes() {
        return superHeroRepository.findAllByOrderById();
    }

    @Override
    @Cacheable(value = "superhero", key = "#id")
    public Superhero getSuperHeroById(Long id) {
        return superHeroRepository.findById(id).orElseThrow(() -> new SuperHeroNotFoundException("SuperHero Not Found with ID: " + id));
    }

    @Override
    @Cacheable(value = "superheroes")
    public List<Superhero> getSuperHeroByNameContaining(String name) {
        return superHeroRepository.findByNameContainingIgnoreCaseOrderById(name);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "superheroes", allEntries = true), },
            put = {@CachePut(value = "superhero", key = "#superHero.getId()") })
    public Superhero createSuperHero(Superhero superHero) {
        superHeroRepository.findByNameIgnoreCase(superHero.getName())
                .ifPresent(superhero -> {
                    throw new SuperHeroAlreadyExistException("Already exist superHero with given name:" + superHero.getName());
                });
        return superHeroRepository.save(superHero);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "superheroes", allEntries = true),
            @CacheEvict(value = "superhero", key = "#id"), })
    public Superhero updateSuperHero(Long id, Superhero superHero) {
        if (id == null) {
            throw new IllegalArgumentException("SuperHero ID cannot be null");
        }
        Superhero existingSuperhero = superHeroRepository.findById(id)
                .orElseThrow(() -> new SuperHeroNotFoundException("SuperHero Not Found with ID: " + id));
        existingSuperhero.setName(superHero.getName());
        return superHeroRepository.save(existingSuperhero);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "superheroes", allEntries = true),
            @CacheEvict(value = "superhero", key = "#id"), })
    public void deleteSuperHeroById(Long id) {
        Superhero existingSuperhero = superHeroRepository.findById(id)
                .orElseThrow(() -> new SuperHeroNotFoundException("SuperHero Not Found with ID: " + id));
        superHeroRepository.deleteById(existingSuperhero.getId());
    }
}
