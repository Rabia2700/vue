package com.isj.gestiondenote.ClientWeb.Model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isj.gestiondenote.ClientWeb.Model.entitiesBiblio.Dons;
import com.isj.gestiondenote.ClientWeb.Model.entitiesBiblio.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonsDto {
    private Integer id;
    private Instant date;
    private Double montant;
    @JsonIgnore
    private Utilisateur utilisateur;
    public static DonsDto fromEntity(Dons dons){
        if (dons.equals(null)){
            return null;
        }

        return DonsDto.builder()
                .id(dons.getId())
                .date(dons.getDate())
                .montant(dons.getMontant())
                .utilisateur(dons.getUtilisateur())
                .build();
    }

    public static Dons toEntity(DonsDto dto){
        if (dto.equals(null)){
            return null;
        }

        Dons dons = new Dons();
        dons.setId(dto.getId());
        dons.setDate(dto.getDate());
        dons.setMontant(dto.getMontant());
        dons.setUtilisateur(dto.getUtilisateur());
        return dons;
    }

}
