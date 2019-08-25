package ru.lifelaboratory.asi.entity;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Investion {

    @SerializedName("id_user")
    Integer idUser;
    @SerializedName("id_project")
    Integer idProject;
    Integer budget;
}
