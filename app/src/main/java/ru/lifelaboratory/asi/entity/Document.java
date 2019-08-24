package ru.lifelaboratory.asi.entity;

public class Document {

    int id;
    int[] idProjects;
    String url;
    String title;
    int type;

    public Document(int id, int[] idProject, String url, String title, int type) {
        this.id = id;
        this.idProjects = idProject;
        this.url = url;
        this.title = title;
        this.type = type;
    }
}
