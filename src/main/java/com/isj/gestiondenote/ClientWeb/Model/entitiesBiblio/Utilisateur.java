package com.isj.gestiondenote.ClientWeb.Model.entitiesBiblio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Utilisateur")
public class Utilisateur extends SuperEntity{
//    private String nom;
//    private String email;
//    private String login;
//    private String passWord;
    private String photo;
    @OneToOne
    @JoinColumn(name = "userid")
    private User user;
    private boolean disponible;
//    @ManyToOne
//    @JoinColumn(name = "idRole")
//    private Roles roles;
//    @OneToMany(mappedBy = "utilisateur")
//    private List<Dons> dons;
//    @OneToMany(mappedBy = "utilisateur")
//    private List<Vente> ventes;
//    @OneToMany(mappedBy = "utilisateur")
//    private List<Emprunt> emprunts;
}
