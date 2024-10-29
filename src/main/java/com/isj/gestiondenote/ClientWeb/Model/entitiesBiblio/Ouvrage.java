package com.isj.gestiondenote.ClientWeb.Model.entitiesBiblio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Ouvrage")
public class Ouvrage extends SuperEntity{
    private String nom;
    private String isbn;
    private String auteur;
    private String maison_Edition;
    private Date date_Edition;
    private Integer nb_exemplaire;
    private String description;
    private String nature;
    private Double prixUnitaire;
    private String photo;
    private String fichier;
    private String type;
    private String genre;
    private boolean disponible;
    @OneToMany(mappedBy = "ouvrage")
    private List<Emprunt> emprunts;
    @OneToMany(mappedBy = "ouvrage")
    private List<LigneVente> ligneVentes;
    @ManyToOne
    @JoinColumn(name = "idCategorie")
    private Categorie categorie;
    @OneToMany(mappedBy = "ouvrage")
    private List<Favoris> favoris;
    @ManyToOne
    @JoinColumn(name = "codeLocalisation")
    private Localisation localisation;
}
