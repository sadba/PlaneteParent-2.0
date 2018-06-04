package com.lab.sadba.loginparent.Model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class InfosEleves extends RealmObject{

    @PrimaryKey
    @SerializedName("ien")
    private String ien;
    @SerializedName("prenom_eleve")
    private String prenom_eleve;
    @SerializedName("nom_eleve")
    private String nom_eleve;
    @SerializedName("date_naissance")
    private String date_naissance;
    @SerializedName("lieu_naissance")
    private String lieu_naissance;
    @SerializedName("code_classe")
    private String code_classe;
    @SerializedName("libelle_classe")
    private String libelle_classe;
    @SerializedName("code_str")
    private String code_str;
    @SerializedName("libelle_structure")
    private String libelle_structure;

    public InfosEleves() {
    }

    public String getIen() {
        return ien;
    }

    public void setIen(String ien) {
        this.ien = ien;
    }

    public String getPrenom_eleve() {
        return prenom_eleve;
    }

    public void setPrenom_eleve(String prenom_eleve) {
        this.prenom_eleve = prenom_eleve;
    }

    public String getNom_eleve() {
        return nom_eleve;
    }

    public void setNom_eleve(String nom_eleve) {
        this.nom_eleve = nom_eleve;
    }

    public String getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(String date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getLieu_naissance() {
        return lieu_naissance;
    }

    public void setLieu_naissance(String lieu_naissance) {
        this.lieu_naissance = lieu_naissance;
    }

    public String getCode_classe() {
        return code_classe;
    }

    public void setCode_classe(String code_classe) {
        this.code_classe = code_classe;
    }

    public String getLibelle_classe() {
        return libelle_classe;
    }

    public void setLibelle_classe(String libelle_classe) {
        this.libelle_classe = libelle_classe;
    }

    public String getCode_str() {
        return code_str;
    }

    public void setCode_str(String code_str) {
        this.code_str = code_str;
    }

    public String getLibelle_structure() {
        return libelle_structure;
    }

    public void setLibelle_structure(String libelle_structure) {
        this.libelle_structure = libelle_structure;
    }
}
