package ru.lifelaboratory.asi.entity;

import com.google.gson.annotations.SerializedName;

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
public class IPUser {

    @SerializedName("id_user")
    Integer id;
    @SerializedName("ИНН")
    String inn;
    @SerializedName("ДеньРождения")
    String birthday;
    @SerializedName("МестоРождения")
    String birthdayPlace;
    @SerializedName("Гражданство")
    String citizenship;
    @SerializedName("КодСтраны")
    String countryCode;
    @SerializedName("ПочтовыйИндекс")
    String index;
    @SerializedName("СубъектРФ")
    String subject;
    @SerializedName("НаименованиеРайона")
    String areaName;
    @SerializedName("Город")
    String typeCity;
    @SerializedName("ГородНазвание")
    String cityName;
    @SerializedName("Улица")
    String street;
    @SerializedName("НомерДом")
    String house;
    @SerializedName("НомерКорпус")
    String block;
    @SerializedName("НомерКвартиры")
    String app;
    @SerializedName("ВидДокумента")
    String typeDoc;
    @SerializedName("ПаспортДатаВыдачи")
    String dateDoc;
    @SerializedName("ПаспортКемВыдан")
    String whoDoc;

}
