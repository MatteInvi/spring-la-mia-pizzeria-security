package org.lessons.java.relation.spring_la_mia_pizzeria_relation.repository;

import java.util.List;

import org.lessons.java.relation.spring_la_mia_pizzeria_relation.model.Offerta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferteRepository extends JpaRepository<Offerta, Integer> {
    public List<Offerta> findByTitoloContainingIgnoreCase(String titolo);
}
