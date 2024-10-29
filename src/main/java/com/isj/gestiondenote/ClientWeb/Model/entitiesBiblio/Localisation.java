package com.isj.gestiondenote.ClientWeb.Model.entitiesBiblio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Localisation")
public class Localisation extends SuperEntity{
    private String nomEmplacement;
    private String numeroEtagere;
    private String section;
    private String code;
    @OneToMany(mappedBy = "localisation")
    private List<Ouvrage> ouvrages;
}
