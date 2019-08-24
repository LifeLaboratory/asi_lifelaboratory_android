package ru.lifelaboratory.asi.entity;

import java.util.List;

public class User {

    int id;
    String fio;
    String login;
    String password;
    double rate;
    String desscription;
    String photoUrl;
    int[] idCategorys;

    public User(int id,
                String fio,
                String login,
                String password,
                double rate,
                String desscription,
                String photoUrl,
                int[] idCategorys) {
        this.id = id;
        this.fio = fio;
        this.login = login;
        this.password = password;
        this.rate = rate;
        this.desscription = desscription;
        this.photoUrl = photoUrl;
        this.idCategorys = idCategorys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getDesscription() {
        return desscription;
    }

    public void setDesscription(String desscription) {
        this.desscription = desscription;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int[] getIdCategorys() {
        return idCategorys;
    }

    public void setIdCategorys(int[] idCategorys) {
        this.idCategorys = idCategorys;
    }
}
