package org.lessons.java.relation.spring_la_mia_pizzeria_relation.controller;

import org.lessons.java.relation.spring_la_mia_pizzeria_relation.model.Ingrediente;
import org.lessons.java.relation.spring_la_mia_pizzeria_relation.model.Pizza;
import org.lessons.java.relation.spring_la_mia_pizzeria_relation.repository.IngredientiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredienti")
public class IngredienteController {

    @Autowired
    IngredientiRepository ingredientiRepository;

    @GetMapping
    public String index(Model model){

        model.addAttribute("ingredienti", ingredientiRepository.findAll());
        return"ingredienti/index";
    }   
    
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id){
        Ingrediente ingredienteDaEliminare = ingredientiRepository.findById(id).get();
        for (Pizza pizza : ingredienteDaEliminare.getPizze() ) {
            pizza.getIngredienti().remove(ingredienteDaEliminare);            
        }
        ingredientiRepository.deleteById(id);
        return "redirect:/ingredienti";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("ingrediente", new Ingrediente());
        return "ingredienti/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ingrediente") Ingrediente formIngrediente, BindingResult bindingResult, Model model){
       if (bindingResult.hasErrors()){
        return "ingredienti/create";
       }

       ingredientiRepository.save(formIngrediente);
       return "redirect:/ingredienti";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id ,Model model){
        model.addAttribute("ingrediente", ingredientiRepository.findById(id));
        return "/ingredienti/edit";
    }

}
