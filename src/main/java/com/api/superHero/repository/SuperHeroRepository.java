package com.api.superHero.repository;

import com.api.superHero.entity.Superhero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuperHeroRepository extends JpaRepository<Superhero, Long> {

    List<Superhero> findAllByOrderById();

    Optional<Superhero> findByNameIgnoreCase (String name);

    List<Superhero> findByNameContainingIgnoreCaseOrderById(String name);
}
