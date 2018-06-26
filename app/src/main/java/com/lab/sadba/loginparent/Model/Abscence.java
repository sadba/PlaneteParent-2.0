package com.lab.sadba.loginparent.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Abscence extends RealmObject {

    @PrimaryKey
    @SerializedName("id_absence")
    @Expose
    private String id_absence;
    @SerializedName("jour")
    @Expose
    private String jour;
    @SerializedName("date_absence")
    @Expose
    private String date_absence;
    @SerializedName("discipline")
    @Expose
    private String discipline;
    @SerializedName("heure_d")
    @Expose
    private String heure_d;
    @SerializedName("heure_f")
    @Expose
    private String heure_f;
    @SerializedName("motif")
    @Expose
    private String motif;

    public Abscence() {
    }

    public String getId_absence() {
        return id_absence;
    }

    public void setId_absence(String id_absence) {
        this.id_absence = id_absence;
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

    public String getHeure_d() {
        return heure_d;
    }

    public void setHeure_d(String heure_d) {
        this.heure_d = heure_d;
    }

    public String getHeure_f() {
        return heure_f;
    }

    public void setHeure_f(String heure_f) {
        this.heure_f = heure_f;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
}
