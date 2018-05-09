package com.lab.sadba.loginparent.Model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Temps extends RealmObject {

    @PrimaryKey
    @SerializedName("id_planing_horaire")
    private String id_planing_horaire;
    @SerializedName("heure_debut")
    private String heure_debut;
    @SerializedName("heure_fin")
    private String heure_fin;
    @SerializedName("libelle_classe")
    private String libelle_classe;
    @SerializedName("code_classe")
    private String code_classe;
    @SerializedName("id_discipline")
    private String id_discipline;
    @SerializedName("libelle_discipline")
    private String libelle_discipline;
    @SerializedName("couleur_discipline")
    private String couleur_discipline;
    @SerializedName("id_classe_physique")
    private String id_classe_physique;
    @SerializedName("libelle_classe_physique")
    private String libelle_classe_physique;
    @SerializedName("jour_planning")
    private String jour_planning;
    @SerializedName("num_jour")
    private String num_jour;

    public String getId_planing_horaire() {
        return id_planing_horaire;
    }

    public void setId_planing_horaire(String id_planing_horaire) {
        this.id_planing_horaire = id_planing_horaire;
    }

    public String getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(String heure_debut) {
        this.heure_debut = heure_debut;
    }

    public String getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(String heure_fin) {
        this.heure_fin = heure_fin;
    }

    public String getLibelle_classe() {
        return libelle_classe;
    }

    public void setLibelle_classe(String libelle_classe) {
        this.libelle_classe = libelle_classe;
    }

    public String getCode_classe() {
        return code_classe;
    }

    public void setCode_classe(String code_classe) {
        this.code_classe = code_classe;
    }

    public String getId_discipline() {
        return id_discipline;
    }

    public void setId_discipline(String id_discipline) {
        this.id_discipline = id_discipline;
    }

    public String getLibelle_discipline() {
        return libelle_discipline;
    }

    public void setLibelle_discipline(String libelle_discipline) {
        this.libelle_discipline = libelle_discipline;
    }

    public String getCouleur_discipline() {
        return couleur_discipline;
    }

    public void setCouleur_discipline(String couleur_discipline) {
        this.couleur_discipline = couleur_discipline;
    }

    public String getId_classe_physique() {
        return id_classe_physique;
    }

    public void setId_classe_physique(String id_classe_physique) {
        this.id_classe_physique = id_classe_physique;
    }

    public String getLibelle_classe_physique() {
        return libelle_classe_physique;
    }

    public void setLibelle_classe_physique(String libelle_classe_physique) {
        this.libelle_classe_physique = libelle_classe_physique;
    }

    public String getJour_planning() {
        return jour_planning;
    }

    public void setJour_planning(String jour_planning) {
        this.jour_planning = jour_planning;
    }

    public String getNum_jour() {
        return num_jour;
    }

    public void setNum_jour(String num_jour) {
        this.num_jour = num_jour;
    }
}
