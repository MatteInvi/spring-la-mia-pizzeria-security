package org.lessons.java.relation.spring_la_mia_pizzeria_relation.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.lessons.java.relation.spring_la_mia_pizzeria_relation.model.Offerta;
import org.lessons.java.relation.spring_la_mia_pizzeria_relation.model.Pizza;
import org.lessons.java.relation.spring_la_mia_pizzeria_relation.repository.IngredientiRepository;
import org.lessons.java.relation.spring_la_mia_pizzeria_relation.repository.OfferteRepository;
import org.lessons.java.relation.spring_la_mia_pizzeria_relation.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/pizze")
public class PizzeController {

    private final IngredientiRepository ingredientiRepository;

    @Autowired
    private PizzaRepository pizzeRepository;

    @Autowired
    private OfferteRepository offerteRepository;

    PizzeController(IngredientiRepository ingredientiRepository) {
        this.ingredientiRepository = ingredientiRepository;
    }

    @GetMapping
    public String index( Authentication authentication, Model model, @RequestParam(required = false) String keyword) {
        List<Pizza> pizze;
        if (keyword != null && !keyword.isEmpty()) {
            pizze = pizzeRepository.findByNomeContainingIgnoreCase(keyword);
        } else {
            pizze = pizzeRepository.findAll();
        }

        model.addAttribute("username", authentication.getName());
        model.addAttribute("pizze", pizze);
        return "pizze/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Optional<Pizza> pizzaOptional = pizzeRepository.findById(id);

        model.addAttribute("ingredienti", ingredientiRepository.findAll());
        model.addAttribute("pizza", pizzaOptional.get());
        return "pizze/show";

    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredienti", ingredientiRepository.findAll());
        return "pizze/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredienti", ingredientiRepository.findAll());
            return "/pizze/create";
        }

        pizzeRepository.save(formPizza);
        return "redirect:/pizze";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ingredienti", ingredientiRepository.findAll());
        model.addAttribute("pizza", pizzeRepository.findById(id).get());
        return "pizze/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "/pizze/edit";
        }

        pizzeRepository.save(formPizza);

        return "redirect:/pizze";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
       
        for (Offerta offerta  : pizzeRepository.findById(id).get().getOfferte()) {
            offerteRepository.delete(offerta);            
        }

         pizzeRepository.deleteById(id);
        return "redirect:/pizze";
    }

    @GetMapping("/{id}/offerta")
    public String offerta(@PathVariable("id") Integer id, Model model) {
        Optional<Pizza> pizzaOptional = pizzeRepository.findById(id);
        if (pizzaOptional.isEmpty()) {
            model.addAttribute("errore", "Non ci sono pizze con id: " + id);
            return "error/index";
        }

        model.addAttribute("pizza", pizzaOptional.get());

        Offerta offerta = new Offerta();
        offerta.setPizza(pizzaOptional.get());
        offerta.setDataInizio(LocalDate.now());
        model.addAttribute("offerta", offerta);
        return "offerte/create";
    }
}
