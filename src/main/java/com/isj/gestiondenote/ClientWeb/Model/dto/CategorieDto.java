package com.isj.gestiondenote.ClientWeb.Model.dto;

//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.isj.gestiondenote.ClientWeb.Model.entitiesBiblio.Categorie;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.isj.gestiondenote.ClientWeb.Model.entitiesBiblio.Categorie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)

public class CategorieDto implements Serializable{
    private Integer id;
    private String code;
    private String nom;
    private String description;
    private String photo;
    @JsonIgnore
    private List<OuvrageDto> ouvrageDtos;
    public static CategorieDto fromEntity(Categorie categorie){
        if (categorie.equals(null)) {
            return null;
        }

        return CategorieDto.builder()
                .id(categorie.getId())
                .code(categorie.getCode())
                .nom(categorie.getNom())
                .photo(categorie.getPhoto())
                .description(categorie.getDescription())
                .build();
    }

    public static Categorie toEntity(CategorieDto dto){
        if (dto.equals(null)){
            return null;
        }
        Categorie categorie = new Categorie();
        categorie.setId(dto.getId());
        categorie.setCode(dto.getCode());
        categorie.setNom(dto.getNom());
        categorie.setPhoto(dto.getPhoto());
        categorie.setDescription(dto.getDescription());

        return categorie;
    }

}