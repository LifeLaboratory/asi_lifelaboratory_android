package ru.lifelaboratory.asi.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.ToString;

@ToString
public class User {

    int id;
    String name;
    String login;
    String password;
    float rate;
    String description;
    @SerializedName("photo")
    String photoUrl;
    List<Category> idCategorys;

    public User(int id,
                String name,
                String login,
                String password,
                float rate,
                String description,
                String photoUrl,
                List<Category> idCategorys) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.rate = rate;
        this.description = description;
        this.photoUrl = photoUrl;
        this.idCategorys = idCategorys;
    }

    public User(String name,
                String login,
                String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public User(String login,
                String password) {
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<Category> getIdCategorys() {
        return idCategorys;
    }

    public void setIdCategorys(List<Category> idCategorys) {
        this.idCategorys = idCategorys;
    }
}
