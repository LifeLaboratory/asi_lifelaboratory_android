package ru.lifelaboratory.asi.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @SerializedName("id_user")
    Integer id;
    String name;
    String login;
    String password;
    float rate;
    String description;
    @SerializedName("photo")
    String photoUrl;
    ArrayList<Category> categories;
    Double budget;
    String card;

    public User(String name,
                String login,
                String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public User(String login,
                String password) {
        this.login = login;
        this.password = password;
    }

}
