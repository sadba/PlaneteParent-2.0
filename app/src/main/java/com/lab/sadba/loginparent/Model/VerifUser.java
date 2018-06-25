package com.lab.sadba.loginparent.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class VerifUser extends RealmObject{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @PrimaryKey
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
    @SerializedName("nombre_enfants")
    @Expose
    private String nombre_enfants;

    public VerifUser() {
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

    public String getNombre_enfants() {
        return nombre_enfants;
    }

    public void setNombre_enfants(String nombre_enfants) {
        this.nombre_enfants = nombre_enfants;
    }
}
