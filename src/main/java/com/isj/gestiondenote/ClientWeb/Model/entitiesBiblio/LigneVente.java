package com.isj.gestiondenote.ClientWeb.Model.entitiesBiblio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LigneVente")
public class LigneVente extends SuperEntity{
    private Double quantite;
    private Double prixTotal;
    @ManyToOne
    @JoinColumn(name = "idOuvrage")
    private Ouvrage ouvrage;
    @ManyToOne
    @JoinColumn(name = "idVente")
    private Vente vente;
}
