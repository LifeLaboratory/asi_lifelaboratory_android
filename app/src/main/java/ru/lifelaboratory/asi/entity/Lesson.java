package ru.lifelaboratory.asi.entity;

public class Lesson {
    Integer id;
    String title;
    Integer type;

    public Lesson(Integer id, String title, Integer type) {
        this.id = id;
        this.title = title;
        this.type = type;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
