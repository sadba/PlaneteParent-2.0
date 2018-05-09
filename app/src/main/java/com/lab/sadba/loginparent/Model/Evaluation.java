package com.lab.sadba.loginparent.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Evaluation extends RealmObject{

    @PrimaryKey
    @SerializedName("id_eval")
    @Expose
    private String id_eval;
    @SerializedName("date_eval")
    @Expose
    private String date_eval;
    @SerializedName("libelle_type_evaluation")
    @Expose
    private String libelle_type_evaluation;
    @SerializedName("libelle_categorie_eval")
    @Expose
    private String libelle_categorie_eval;
    @SerializedName("libelle_periode_eval")
    @Expose
    private String libelle_periode_eval;
    @SerializedName("libelle_discipline")
    @Expose
    private String libelle_discipline;

    public Evaluation() {
    }

    public String getId_eval() {
        return id_eval;
    }

    public void setId_eval(String id_eval) {
        this.id_eval = id_eval;
    }

    public String getDate_eval() {
        return date_eval;
    }

    public void setDate_eval(String date_eval) {
        this.date_eval = date_eval;
    }

    public String getLibelle_type_evaluation() {
        return libelle_type_evaluation;
    }

    public void setLibelle_type_evaluation(String libelle_type_evaluation) {
        this.libelle_type_evaluation = libelle_type_evaluation;
    }

    public String getLibelle_categorie_eval() {
        return libelle_categorie_eval;
    }

    public void setLibelle_categorie_eval(String libelle_categorie_eval) {
        this.libelle_categorie_eval = libelle_categorie_eval;
    }

    public String getLibelle_periode_eval() {
        return libelle_periode_eval;
    }

    public void setLibelle_periode_eval(String libelle_periode_eval) {
        this.libelle_periode_eval = libelle_periode_eval;
    }

    public String getLibelle_discipline() {
        return libelle_discipline;
    }

    public void setLibelle_discipline(String libelle_discipline) {
        this.libelle_discipline = libelle_discipline;
    }
}
