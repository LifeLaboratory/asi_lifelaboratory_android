package ru.lifelaboratory.asi.entity;

public class Document {

    int id;
    int[] idProjects;
    String url;
    String title;
    int type;
    String logo;
    String description;

    public Document(int id, int[] idProject, String url, String title, int type, String description, String logo) {
        this.id = id;
        this.description = description;
        this.idProjects = idProject;
        this.url = url;
        this.title = title;
        this.type = type;
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getIdProjects() {
        return idProjects;
    }

    public void setIdProjects(int[] idProjects) {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
