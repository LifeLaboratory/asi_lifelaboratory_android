package ru.lifelaboratory.asi.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.lifelaboratory.asi.entity.StatusSignIn;
import ru.lifelaboratory.asi.entity.StatusSignUp;
import ru.lifelaboratory.asi.entity.User;

public interface UserService {

    @POST("register")
    Call<StatusSignUp> register(@Body User user);

    @POST("auth")
    Call<StatusSignIn> auth(@Body User user);

}