package com.isj.gestiondenote.ClientWeb.Model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpruntDto {
    @JsonProperty("id")
    private Integer id;
    private String motif;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDebut;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateRestitution;
    private String etat;
    @JsonProperty("utilisateur")
    private UtilisateurDto utilisateur;
    @JsonProperty("ouvrage")
    private OuvrageDto ouvrage;

}
