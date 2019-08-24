package ru.lifelaboratory.asi.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Budget {

    @SerializedName("id_user")
    Integer idUser;
    ArrayList<Integer> idProjects;
    ArrayList<Float> budgets;
}
