package ru.lifelaboratory.asi.entity;

import java.util.List;

public class Project {

    Integer id;
    String title;
    String description;
    String photo;
    Integer idAuthor;
    float rate;
    List<Category> categories;
    float budget;


    public Project(Integer id, String title,
                   String description,
                   String photo,
                   Integer id_user,
                   float budget,
                   float rate,
                   List<Category> categories) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.photo = photo;
        this.idAuthor = id_user;
        this.budget = budget;
        this.rate = rate;
        this.categories = categories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(Integer idAuthor) {
        this.idAuthor = idAuthor;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }
}
