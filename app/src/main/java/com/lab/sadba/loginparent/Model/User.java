package com.lab.sadba.loginparent.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class User extends RealmObject{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("ien_parent")
    @Expose
    private String ien_parent;
    @SerializedName("prenom_parent")
    @Expose
    private String prenom_parent;
    @SerializedName("nom_parent")
    @Expose
    private String nom_parent;
    @SerializedName("type_affiliation")
    @Expose
    private String type_affiliation;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("adresse")
    @Expose
    private String adresse;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("pays")
    @Expose
    private String pays;
    @SerializedName("nombre_enfants")
    @Expose
    private String nombre_enfants;


    public User() {
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

    public String getIen_parent() {
        return ien_parent;
    }

    public void setIen_parent(String ien_parent) {
        this.ien_parent = ien_parent;
    }

    public String getPrenom_parent() {
        return prenom_parent;
    }

    public void setPrenom_parent(String prenom_parent) {
        this.prenom_parent = prenom_parent;
    }

    public String getNom_parent() {
        return nom_parent;
    }

    public void setNom_parent(String nom_parent) {
        this.nom_parent = nom_parent;
    }

    public String getType_affiliation() {
        return type_affiliation;
    }

    public void setType_affiliation(String type_affiliation) {
        this.type_affiliation = type_affiliation;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getNombre_enfants() {
        return nombre_enfants;
    }

    public void setNombre_enfants(String nombre_enfants) {
        this.nombre_enfants = nombre_enfants;
    }
}
