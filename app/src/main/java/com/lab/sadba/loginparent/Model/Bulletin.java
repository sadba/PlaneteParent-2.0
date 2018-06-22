package com.lab.sadba.loginparent.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Bulletin extends RealmObject{

    @PrimaryKey
    @SerializedName("id_semestre")
    @Expose
    private String id_semestre;
    @SerializedName("libelle_semestre")
    @Expose
    private String libelle_semestre;
    @SerializedName("chemin_bulletin")
    @Expose
    private String chemin_bulletin;

    public Bulletin() {
    }

    public String getId_semestre() {
        return id_semestre;
    }

    public void setId_semestre(String id_semestre) {
        this.id_semestre = id_semestre;
    }

    public String getLibelle_semestre() {
        return libelle_semestre;
    }

    public void setLibelle_semestre(String libelle_semestre) {
        this.libelle_semestre = libelle_semestre;
    }

    public String getChemin_bulletin() {
        return chemin_bulletin;
    }

    public void setChemin_bulletin(String chemin_bulletin) {
        this.chemin_bulletin = chemin_bulletin;
    }
}
