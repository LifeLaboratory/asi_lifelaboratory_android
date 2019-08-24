package ru.lifelaboratory.asi.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.lifelaboratory.asi.entity.StatusSignIn;
import ru.lifelaboratory.asi.entity.StatusSignUp;
import ru.lifelaboratory.asi.entity.User;

public interface UserService {

    @POST("/register")
    Call<StatusSignUp> register(@Body User user);

    @POST("/auth")
    Call<StatusSignIn> auth(@Body User user);


    @GET("/profile/{id}")
    Call<User> profile(@Path("id") Integer idUser);
}