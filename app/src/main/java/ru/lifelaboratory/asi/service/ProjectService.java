package ru.lifelaboratory.asi.service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.lifelaboratory.asi.entity.Category;
import ru.lifelaboratory.asi.entity.Investor;
import ru.lifelaboratory.asi.entity.Project;

public interface ProjectService {

    @POST("/project/filter")
    Call<ArrayList<Project>> getAllProject(@Body ArrayList<Category> categories);

    @POST("/project/filter")
    Call<ArrayList<Project>> getAllProject(@Body Integer idUser);

    @GET("/project/{idProject}")
    Call<Project> getProject(@Path("idProject") Integer idProject);

    @GET("/project")
    Call<List<Project>> getAllProject();

    @POST("/project")
    Call<Object> addProject(@Body Project project);

    @GET("/project/{idProject}/category")
    Call<ArrayList<Category>> getCategory(@Path("idProject") Integer idProject);

    @GET("/project/{idProject}/budget")
    Call<ArrayList<Investor>> getInvistition(@Path("idProject") Integer idProject);
}
