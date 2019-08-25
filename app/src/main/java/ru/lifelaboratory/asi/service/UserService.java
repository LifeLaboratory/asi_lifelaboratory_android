package ru.lifelaboratory.asi.service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.lifelaboratory.asi.entity.CV;
import ru.lifelaboratory.asi.entity.Category;
import ru.lifelaboratory.asi.entity.HistoryBudget;
import ru.lifelaboratory.asi.entity.Investion;
import ru.lifelaboratory.asi.entity.StatusSignIn;
import ru.lifelaboratory.asi.entity.StatusSignUp;
import ru.lifelaboratory.asi.entity.User;

public interface UserService {

    @POST("/register")
    Call<StatusSignUp> register(@Body User user);

    @POST("/auth")
    Call<StatusSignIn> auth(@Body User user);

    @GET("/cv")
    Call<List<CV>> getAllCV();

    @POST("/cv")
    Call<Object> addCV(@Body CV cv);

    @GET("/profile/{id}")
    Call<User> profile(@Path("id") Integer idUser);

    @GET("/investors")
    Call<ArrayList<User>> getInvestors();

    @POST("/budget")
    Call<Object> investMoney(@Body Investion investion);

    @GET("/user/{idUser}/category")
    Call<ArrayList<Category>> getCategory(@Path("idUser") Integer idUser);

    @GET("/budget/{idUser}")
    Call<ArrayList<HistoryBudget>> getInvestitions(@Path("idUser") Integer idUser);

}