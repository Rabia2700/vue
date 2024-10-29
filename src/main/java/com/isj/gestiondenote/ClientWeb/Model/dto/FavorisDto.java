package com.isj.gestiondenote.ClientWeb.Model.dto;

import com.isj.gestiondenote.ClientWeb.Model.entitiesBiblio.Favoris;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavorisDto {
    private Integer id;
    private Integer idUtilisateur;
    private OuvrageDto ouvrageDto;

    public static FavorisDto fromEntity(Favoris favoris){

        if(favoris.equals(null)){
            return null;
        }

        return FavorisDto.builder()
                .id(favoris.getId())
                .idUtilisateur(favoris.getIdUtilisateur())
                .ouvrageDto(OuvrageDto.fromEntity(favoris.getOuvrage()))
                .build();
    }

    public static Favoris toEntity(FavorisDto dto){

        if (dto.equals(null)){
            return null;
        }

        Favoris favoris = new Favoris();
        favoris.setId(dto.getId());
        favoris.setIdUtilisateur(dto.getIdUtilisateur());
        favoris.setOuvrage(OuvrageDto.toEntity(dto.getOuvrageDto()));

        return favoris;

    }
}
