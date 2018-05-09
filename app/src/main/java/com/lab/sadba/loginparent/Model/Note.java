package com.lab.sadba.loginparent.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Note extends RealmObject {

    @PrimaryKey
    @SerializedName("id_note")
    @Expose
    private String id_note;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("libelle_discipline")
    @Expose
    private String libelle_discipline;
    @SerializedName("date_eval")
    @Expose
    private String date_eval;
    @SerializedName("devoir")
    private String devoir;

    public Note() {
    }

    public String getId_note() {
        return id_note;
    }

    public void setId_note(String id_note) {
        this.id_note = id_note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLibelle_discipline() {
        return libelle_discipline;
    }

    public void setLibelle_discipline(String libelle_discipline) {
        this.libelle_discipline = libelle_discipline;
    }

    public String getDate_eval() {
        return date_eval;
    }

    public void setDate_eval(String date_eval) {
        this.date_eval = date_eval;
    }

    public String getDevoir() {
        return devoir;
    }

    public void setDevoir(String devoir) {
        this.devoir = devoir;
    }
}
