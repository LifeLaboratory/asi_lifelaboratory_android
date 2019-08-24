package ru.lifelaboratory.asi.entity;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Document {

    Integer id;
    @SerializedName("id_projects")
    Integer[] idProjects;
    String url;
    String title;
    Integer type;
    String photo;
    String description;
}
