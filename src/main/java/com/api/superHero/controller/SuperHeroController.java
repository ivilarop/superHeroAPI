package com.api.superHero.controller;

import com.api.superHero.entity.Superhero;
import com.api.superHero.service.SuperHeroService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SuperHeroController {

    private final SuperHeroService superHeroService;

    @Operation(summary = "Get All SuperHeroes")
    @GetMapping(path = "/superHeroes")
    public ResponseEntity<List<Superhero>> getAllSuperHeroes() {
        return ResponseEntity.ok().body(superHeroService.getAllSuperHeroes());
    }

    @Operation(summary = "Get SuperHero by ID")
    @GetMapping(path = "/superHeroes/{id}")
    public ResponseEntity<Superhero> getSuperHeroById(@PathVariable Long id) {
        return ResponseEntity.ok().body(superHeroService.getSuperHeroById(id));
    }

    @Operation(summary = "Get SuperHeroes By Name Containing")
    @GetMapping(path = "/superHeroes/name/{name}")
    public ResponseEntity<List<Superhero>> getSuperHeroesByNameContaining(@PathVariable String name) {
        return ResponseEntity.ok().body(superHeroService.getSuperHeroByNameContaining(name));
    }

    @Operation(summary = "Create new SuperHero")
    @PostMapping(path = "/superHeroes",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Superhero> saveSuperHero(@RequestBody Superhero superHeroDto){
        Superhero newSuperhero = superHeroService.createSuperHero(superHeroDto);
        return new ResponseEntity<>(newSuperhero, HttpStatus.CREATED);
    }

    @Operation(summary = "Update SuperHero")
    @PutMapping(path = "/superHeroes/{id}")
    public ResponseEntity<Superhero> updateSuperHero(@PathVariable(value = "id") Long id,
                                                     @RequestBody Superhero superHeroDto) {
        return ResponseEntity.ok().body(superHeroService.updateSuperHero(id,superHeroDto));
    }

    @Operation(summary = "Delete SuperHero by ID")
    @DeleteMapping(value = "/superHeroes/{id}")
    public ResponseEntity<String> deleteSuperHero(@PathVariable Long id) {
        superHeroService.deleteSuperHeroById(id);
        return new ResponseEntity<>(("SuperHero deleted successfully- SuperHero ID:" + id), HttpStatus.OK);
    }
}
