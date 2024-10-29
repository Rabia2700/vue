package com.isj.gestiondenote.ClientWeb.Model.entitiesBiblio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Favoris")
public class Favoris extends SuperEntity{
    @Column(name = "idUtilisateur")
    private Integer idUtilisateur;
    @ManyToOne
    @JoinColumn(name = "idOuvrage")
    private Ouvrage ouvrage;
}
