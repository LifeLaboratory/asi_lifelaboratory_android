package ru.lifelaboratory.asi.entity;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class StatusSignIn {

    @Getter @Setter
    private Integer error;
    @Getter @Setter
    @SerializedName("id_user")
    private Integer idUser;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private Double rate;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private String photo;

}
