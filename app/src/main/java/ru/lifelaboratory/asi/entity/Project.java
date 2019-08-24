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

    Integer id;
    String title;
    String description;
    @SerializedName("photo_url")

    String photoUrl;
    Integer id_author;
    float rate;
    List<Category> categories;
    float budget;

}
