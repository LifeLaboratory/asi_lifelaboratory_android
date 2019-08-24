package ru.lifelaboratory.asi.entity;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class StatusSignUp {

    @Getter @Setter
    @SerializedName("id_user")
    private Integer idUser;
    @Getter @Setter
    private Integer errorCode;

}
