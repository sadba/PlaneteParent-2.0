package com.lab.sadba.loginparent.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Enfant extends RealmObject{



    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("ien_eleve")
    @Expose
    private String ien_eleve;
    @SerializedName("id_parent")
    @Expose
    private String id_parent;
    @SerializedName("type_affiliation")
    @Expose
    private String type_affiliation;
    @SerializedName("prenom_eleve")
    @Expose
    private String prenom_eleve;
    @SerializedName("nom_eleve")
    @Expose
    private String nom_eleve;
    @SerializedName("date_naiss_eleve")
    @Expose
    private String date_naiss_eleve;
    @SerializedName("lieu_naiss_eleve")
    @Expose
    private String lieu_naiss_eleve;
    @SerializedName("sexe_eleve")
    @Expose
    private String sexe_eleve;
    @SerializedName("id_cycle")
    @Expose
    private String id_cycle;
    @SerializedName("id_niveau")
    @Expose
    private String id_niveau;
    @SerializedName("id_etablissement")
    @Expose
    private String id_etablissement;
    @SerializedName("libelle_etablissement")
    @Expose
    private String libelle_etablissement;
    @SerializedName("libelle_cycle")
    @Expose
    private String libelle_cycle;
    @SerializedName("libelle_niveau")
    @Expose
    private String libelle_niveau;


    public Enfant() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIen_eleve() {
        return ien_eleve;
    }

    public void setIen_eleve(String ien_eleve) {
        this.ien_eleve = ien_eleve;
    }

    public String getId_parent() {
        return id_parent;
    }

    public void setId_parent(String id_parent) {
        this.id_parent = id_parent;
    }

    public String getType_affiliation() {
        return type_affiliation;
    }

    public void setType_affiliation(String type_affiliation) {
        this.type_affiliation = type_affiliation;
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

    public String getDate_naiss_eleve() {
        return date_naiss_eleve;
    }

    public void setDate_naiss_eleve(String date_naiss_eleve) {
        this.date_naiss_eleve = date_naiss_eleve;
    }

    public String getLieu_naiss_eleve() {
        return lieu_naiss_eleve;
    }

    public void setLieu_naiss_eleve(String lieu_naiss_eleve) {
        this.lieu_naiss_eleve = lieu_naiss_eleve;
    }

    public String getSexe_eleve() {
        return sexe_eleve;
    }

    public void setSexe_eleve(String sexe_eleve) {
        this.sexe_eleve = sexe_eleve;
    }

    public String getId_cycle() {
        return id_cycle;
    }

    public void setId_cycle(String id_cycle) {
        this.id_cycle = id_cycle;
    }

    public String getId_niveau() {
        return id_niveau;
    }

    public void setId_niveau(String id_niveau) {
        this.id_niveau = id_niveau;
    }

    public String getId_etablissement() {
        return id_etablissement;
    }

    public void setId_etablissement(String id_etablissement) {
        this.id_etablissement = id_etablissement;
    }

    public String getLibelle_etablissement() {
        return libelle_etablissement;
    }

    public void setLibelle_etablissement(String libelle_etablissement) {
        this.libelle_etablissement = libelle_etablissement;
    }

    public String getLibelle_cycle() {
        return libelle_cycle;
    }

    public void setLibelle_cycle(String libelle_cycle) {
        this.libelle_cycle = libelle_cycle;
    }

    public String getLibelle_niveau() {
        return libelle_niveau;
    }

    public void setLibelle_niveau(String libelle_niveau) {
        this.libelle_niveau = libelle_niveau;
    }
}
