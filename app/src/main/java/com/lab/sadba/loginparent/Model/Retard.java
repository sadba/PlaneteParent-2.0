package com.lab.sadba.loginparent.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Retard extends RealmObject {

    @PrimaryKey
    @SerializedName("id_retard")
    @Expose
    private String id_retard;
    @SerializedName("jour")
    @Expose
    private String jour;
    @SerializedName("date_absence")
    @Expose
    private String date_absence;
    @SerializedName("discipline")
    @Expose
    private String discipline;
    @SerializedName("duree")
    @Expose
    private String duree;
    @SerializedName("heure_debut_cours")
    @Expose
    private String heure_debut_cours;
    @SerializedName("motif")
    @Expose
    private String motif;

    public Retard() {
    }

    public String getId_retard() {
        return id_retard;
    }

    public void setId_retard(String id_retard) {
        this.id_retard = id_retard;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getDate_absence() {
        return date_absence;
    }

    public void setDate_absence(String date_absence) {
        this.date_absence = date_absence;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getHeure_debut_cours() {
        return heure_debut_cours;
    }

    public void setHeure_debut_cours(String heure_debut_cours) {
        this.heure_debut_cours = heure_debut_cours;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
}
