package ru.lifelaboratory.asi.entity;

public class Project {

    int id;
    String title;
    String description;
    String photoUrl;
    int id_user;
    String theme;
    double money;
    double rate;
    int[] idCategorys;

    public Project(int id, String title,
                   String description,
                   String photoUrl,
                   int id_user,
                   String theme,
                   double money,
                   double rate,
                   int[] idCategorys) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.photoUrl = photoUrl;
        this.id_user = id_user;
        this.theme = theme;
        this.money = money;
        this.rate = rate;
        this.idCategorys = idCategorys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int[] getIdCategorys() {
        return idCategorys;
    }

    public void setIdCategorys(int[] idCategorys) {
        this.idCategorys = idCategorys;
    }
}
