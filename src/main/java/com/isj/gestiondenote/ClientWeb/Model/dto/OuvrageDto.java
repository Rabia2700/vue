package com.isj.gestiondenote.ClientWeb.Model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.positif.gestionBibliotheques.Model.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.isj.gestiondenote.ClientWeb.Model.entitiesBiblio.Emprunt;
import com.isj.gestiondenote.ClientWeb.Model.entitiesBiblio.Favoris;
import com.isj.gestiondenote.ClientWeb.Model.entitiesBiblio.LigneVente;
import com.isj.gestiondenote.ClientWeb.Model.entitiesBiblio.Ouvrage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OuvrageDto {
    @JsonProperty("id")
    private Integer id;
    private String nom;
    private String isbn;
    private String auteur;
    private String maison_Edition;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date_Edition;
    private Integer nb_exemplaire;
    private String description;
    private String nature;
    private Double prixUnitaire;
    private String photo;
    private String fichier;
    private String type;
    private String genre;
    private boolean disponible;
    private LocalisationDto localisationDto;
    private CategorieDto categorieDto;
    @JsonIgnore
    private List<EmpruntDto> emprunts;
    @JsonIgnore
    private List<FavorisDto> favorisDtos;
    @JsonIgnore
    private List<LigneVenteDto> ligneVentes;

    public static OuvrageDto fromEntity(Ouvrage ouvrage){
        if(ouvrage.equals(null)){
            return null;
        }
        List<EmpruntDto> empruntDtos = new ArrayList<>();
        List<LigneVenteDto> ligneVenteDtos = new ArrayList<>();
        List<FavorisDto> favorisDtos1 = new ArrayList<>();
        return OuvrageDto.builder()
                .id(ouvrage.getId())
                .isbn(ouvrage.getIsbn())
                .nom(ouvrage.getNom())
                .auteur(ouvrage.getAuteur())
                .maison_Edition(ouvrage.getMaison_Edition())
                .date_Edition(ouvrage.getDate_Edition())
                .nb_exemplaire(ouvrage.getNb_exemplaire())
                .description(ouvrage.getDescription())
                .nature(ouvrage.getNature())
                .prixUnitaire(ouvrage.getPrixUnitaire())
                .photo(ouvrage.getPhoto())
                .fichier(ouvrage.getFichier())
                .type(ouvrage.getType())
                .genre(ouvrage.getGenre())
                .disponible(ouvrage.isDisponible())
                .categorieDto(CategorieDto.fromEntity(ouvrage.getCategorie()))
                .localisationDto(LocalisationDto.fromEntity(ouvrage.getLocalisation()))
                .emprunts(empruntDtos)
                .ligneVentes(ligneVenteDtos)
                .favorisDtos(favorisDtos1)
                .build();
    }

    public static Ouvrage toEntity(OuvrageDto dto){
        if (dto.equals(null)){
            return null;
        }

        Ouvrage ouvrage = new Ouvrage();
        ouvrage.setId(dto.getId());
//        ouvrage.setIsbn(dto.getIsbn());
        ouvrage.setNom(dto.getNom());
        ouvrage.setAuteur(dto.getAuteur());
        ouvrage.setNb_exemplaire(dto.getNb_exemplaire());
        ouvrage.setDescription(dto.getDescription());
        ouvrage.setNature(dto.getNature());
        ouvrage.setMaison_Edition(dto.getMaison_Edition());
        ouvrage.setDate_Edition(dto.getDate_Edition());
        ouvrage.setPrixUnitaire(dto.getPrixUnitaire());
        ouvrage.setDisponible(dto.isDisponible());
//        ouvrage.setPhoto(dto.getPhoto());
//        ouvrage.setFichier(dto.getFichier());
//        ouvrage.setType(dto.getType());
        ouvrage.setGenre(dto.getGenre());
        ouvrage.setCategorie(CategorieDto.toEntity(dto.getCategorieDto()));
        ouvrage.setLocalisation(LocalisationDto.toEntity(dto.getLocalisationDto()));
//        List<Emprunt> emprunts1 = new ArrayList<>();
//        ouvrage.setEmprunts(emprunts1);
//        List<LigneVente> ligneVentes1 = new ArrayList<>();
//        ouvrage.setLigneVentes(ligneVentes1);
//        List<Favoris> favoris = new ArrayList<>();
//        ouvrage.setFavoris(favoris);
        return ouvrage;
    }
}
