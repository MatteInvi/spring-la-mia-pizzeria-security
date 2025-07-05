


 package org.lessons.java.relation.spring_la_mia_pizzeria_relation.repository;

import java.util.List;

import org.lessons.java.relation.spring_la_mia_pizzeria_relation.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

     public List<Pizza> findByNomeContainingIgnoreCase(String nome);

    
}