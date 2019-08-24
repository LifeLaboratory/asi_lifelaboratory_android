package ru.lifelaboratory.asi.entity;

public class Document {

    Integer id;
    Integer[] idProjects;
    String url;
    String title;
    Integer type;
    String photo;
    String description;

    public Document(Integer id, Integer[] idProject, String url, String title, Integer type, String photo) {
        this.id = id;
        this.idProjects = idProject;
        this.url = url;
        this.title = title;
        this.type = type;
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer[] getIdProjects() {
        return idProjects;
    }

    public void setIdProjects(Integer[] idProjects) {
        this.idProjects = idProjects;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
