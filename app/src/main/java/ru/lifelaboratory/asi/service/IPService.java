package ru.lifelaboratory.asi.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.lifelaboratory.asi.entity.IPUser;

public interface IPService {

    @POST("/print_form")
    Call<Object> register(@Body IPUser user);

    @POST("/print_form/generate")
    Call<Object> generate(@Body IPUser user);

}
