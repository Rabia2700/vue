package com.isj.gestiondenote.ClientWeb.Model.entitiesBiblio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Emprunt")
public class Emprunt extends SuperEntity {
    private String motif;
    private Date dateDebut;
    private Date dateFin;
    private Date dateRestitution;
    private String etat;
    @ManyToOne
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur utilisateur;
    @ManyToOne
    @JoinColumn(name = "idOuvrage")
    private Ouvrage ouvrage;

}
