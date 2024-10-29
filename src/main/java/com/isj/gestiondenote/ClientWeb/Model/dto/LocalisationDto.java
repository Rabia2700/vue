package com.isj.gestiondenote.ClientWeb.Model.dto;

//import com.isj.gestiondenote.ClientWeb.Model.entitiesBiblio.Localisation;
import com.isj.gestiondenote.ClientWeb.Model.entitiesBiblio.Localisation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalisationDto {

    private Integer id;
    private String nomEmplacement;
    private String numeroEtagere;
    private String section;
    private String code;
    private List<OuvrageDto> ouvrageDtos;

    public static LocalisationDto fromEntity(Localisation localisation){
        if (localisation.equals(null)){
            return null;
        }

        return LocalisationDto.builder()
                .id(localisation.getId())
                .nomEmplacement(localisation.getNomEmplacement())
                .numeroEtagere(localisation.getNumeroEtagere())
                .section(localisation.getSection())
                .code(localisation.getCode())
                .build();
    }

    public static Localisation toEntity(LocalisationDto dto){
        if (dto.equals(null)){
            return null;
        }

        Localisation localisation = new Localisation();
        localisation.setId(dto.getId());
        localisation.setNomEmplacement(dto.getNomEmplacement());
        localisation.setNumeroEtagere(dto.getNumeroEtagere());
        localisation.setSection(dto.getSection());
        localisation.setCode(dto.getCode());
        return localisation;
    }

}
