package com.isj.gestiondenote.ClientWeb.Model.entitiesBiblio;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "Roles")
public class Roles extends SuperEntity{
    private String nom;
//    @JsonIgnore
//    @OneToMany(mappedBy = "roles")
//    private List<Utilisateur> utilisateurs;
}
