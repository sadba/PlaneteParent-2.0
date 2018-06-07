package com.lab.sadba.loginparent.Model;

public class PostRegisterUser {

    String ien;
    String code_verif;
    String password;

    public PostRegisterUser() {
    }

    public String getIen() {
        return ien;
    }

    public void setIen(String ien) {
        this.ien = ien;
    }

    public String getCode_verif() {
        return code_verif;
    }

    public void setCode_verif(String telephone) {
        this.code_verif = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
