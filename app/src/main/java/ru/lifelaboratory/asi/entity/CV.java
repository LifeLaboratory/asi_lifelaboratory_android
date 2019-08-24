package ru.lifelaboratory.asi.entity;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CV {

    @SerializedName("id_cv")
    Integer id;
    @SerializedName("id_user")
    Integer idUser;
    String url;
    String title;
    String description;

}
