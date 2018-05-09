package com.lab.sadba.loginparent.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class InfoEtab extends RealmObject{


    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @PrimaryKey
    @SerializedName("code_admin")
    @Expose
    private String code_admin;
    @SerializedName("nom_struct")
    @Expose
    private String nom_struct;
    @SerializedName("libelle_type_systeme_ens")
    @Expose
    private String libelle_type_systeme_ens;
    @SerializedName("tel_chef_struct")
    @Expose
    private String tel_chef_struct;
    @SerializedName("tel_struct")
    @Expose
    private String tel_struct;

    public InfoEtab() {
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

    public String getCode_admin() {
        return code_admin;
    }

    public void setCode_admin(String code_admin) {
        this.code_admin = code_admin;
    }

    public String getNom_struct() {
        return nom_struct;
    }

    public void setNom_struct(String nom_struct) {
        this.nom_struct = nom_struct;
    }

    public String getLibelle_type_systeme_ens() {
        return libelle_type_systeme_ens;
    }

    public void setLibelle_type_systeme_ens(String libelle_type_systeme_ens) {
        this.libelle_type_systeme_ens = libelle_type_systeme_ens;
    }

    public String getTel_chef_struct() {
        return tel_chef_struct;
    }

    public void setTel_chef_struct(String tel_chef_struct) {
        this.tel_chef_struct = tel_chef_struct;
    }

    public String getTel_struct() {
        return tel_struct;
    }

    public void setTel_struct(String tel_struct) {
        this.tel_struct = tel_struct;
    }
}
