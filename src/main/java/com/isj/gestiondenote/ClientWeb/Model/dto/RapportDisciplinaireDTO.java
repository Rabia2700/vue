package com.isj.gestiondenote.ClientWeb.Model.dto;

public class RapportDisciplinaireDTO {
    private String nomPersonnel;
    private String dateRapport;
    private String detailsRapport;
    private String matricule;

    // Getters et Setters

    public String getNomPersonnel() {
        return nomPersonnel;
    }

    public void setNomPersonnel(String nomPersonnel) {
        this.nomPersonnel = nomPersonnel;
    }

    public String getDateRapport() {
        return dateRapport;
    }

    public void setDateRapport(String dateRapport) {
        this.dateRapport = dateRapport;
    }

    public String getDetailsRapport() {
        return detailsRapport;
    }

    public void setDetailsRapport(String detailsRapport) {
        this.detailsRapport = detailsRapport;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
}

