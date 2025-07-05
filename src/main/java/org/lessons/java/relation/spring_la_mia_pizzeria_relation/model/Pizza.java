package org.lessons.java.relation.spring_la_mia_pizzeria_relation.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "pizze")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Il nome non può essere vuoto")
    private String nome;

    @Lob
    @NotBlank(message = "La descrizione non può essere vuota")
    private String descrizione;

    @Lob
    private String urlFoto;

    @NotNull(message = "Il prezzo non può essere vuoto")
    @Positive(message = "Il prezzo non può essere inferiore a 1")
    private BigDecimal prezzo;

    @OneToMany(mappedBy = "pizza")
    private List<Offerta> offerte;

    @ManyToMany
    @JoinTable(name = "ingrediente_pizza", joinColumns = @JoinColumn(name = "pizza_id"), inverseJoinColumns = @JoinColumn(name = "ingrediente_id"))
    private List<Ingrediente> ingredienti;

    public List<Ingrediente> getIngredienti() {
        return this.ingredienti;
    }

    public void setIngredienti(List<Ingrediente> ingredienti) {
        this.ingredienti = ingredienti;
    }

    public List<Offerta> getOfferte() {
        return this.offerte;
    }

    public void setOfferte(List<Offerta> offerte) {
        this.offerte = offerte;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getUrlFoto() {
        return this.urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public BigDecimal getPrezzo() {
        return this.prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {

        this.prezzo = prezzo;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
