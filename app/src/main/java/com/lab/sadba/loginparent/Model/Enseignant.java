package com.lab.sadba.loginparent.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Enseignant extends RealmObject{

    @PrimaryKey
    @SerializedName("id_enseignant")
    @Expose
    private String id_enseignant;
    @SerializedName("ien_enseignant")
    @Expose
    private String ien_enseignant;
    @SerializedName("nom_complet")
    @Expose
    private String nom_complet;
    @SerializedName("specialite")
    @Expose
    private String specialite;


    public Enseignant() {
    }

    public String getId_enseignant() {
        return id_enseignant;
    }

    public void setId_enseignant(String id_enseignant) {
        this.id_enseignant = id_enseignant;
    }

    public String getIen_enseignant() {
        return ien_enseignant;
    }

    public void setIen_enseignant(String ien_enseignant) {
        this.ien_enseignant = ien_enseignant;
    }

    public String getNom_complet() {
        return nom_complet;
    }

    public void setNom_complet(String nom_complet) {
        this.nom_complet = nom_complet;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
}
