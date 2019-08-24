package ru.lifelaboratory.asi.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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
public class Project {

    @SerializedName("id_project")
    Integer id;
    String title;
    String description;
    String photo;
    @SerializedName("id_user")
    Integer idAuthor;
    float rate;
    List<Category> categories;
    float budget;

}
