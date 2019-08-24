package ru.lifelaboratory.asi.entity;

public class CV {

    Integer id;
    Integer idUser;
    String url;

    public CV(int id, int idUser, String url) {
        this.id = id;
        this.idUser = idUser;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
