package org.lessons.java.relation.spring_la_mia_pizzeria_relation.controller;

import java.util.List;

import org.lessons.java.relation.spring_la_mia_pizzeria_relation.model.Offerta;
import org.lessons.java.relation.spring_la_mia_pizzeria_relation.repository.OfferteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/offerte")
public class OffertaController {

    @Autowired
    private OfferteRepository offerteRepository;

    @GetMapping
    public String index(@RequestParam(required = false) String keyword, Model model) {
        List<Offerta> offerte;
        if (keyword != null && !keyword.isEmpty() ) {
            offerte = offerteRepository.findByTitoloContainingIgnoreCase(keyword);
        } else {

        offerte = offerteRepository.findAll();
        }
        model.addAttribute("offerte", offerte);
        return "offerte/index";
    }

    @PostMapping
    public String store(@Valid @ModelAttribute("offerta") Offerta offertaForm, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("offerta", offertaForm);
            return "offerte/create";
        }

        offerteRepository.save(offertaForm);

        return "redirect:/pizze";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("offerta", offerteRepository.findById(id).get());
        return "offerte/edit";
    }

    @PostMapping("/{id}/edit")
    public String update(@Valid @ModelAttribute("offerta") Offerta offertaForm, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("offerta", offertaForm);
            return "offerte/create";
        }

        offerteRepository.save(offertaForm);

        return "redirect:/offerte";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        offerteRepository.deleteById(id);
        return "redirect:/offerte";
    }

}
