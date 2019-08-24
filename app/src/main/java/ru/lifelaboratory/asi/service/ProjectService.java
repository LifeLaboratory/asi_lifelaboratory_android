package ru.lifelaboratory.asi.service;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.lifelaboratory.asi.entity.Category;
import ru.lifelaboratory.asi.entity.Project;

public interface ProjectService {

    @POST("project/filter")
    Call<ArrayList<Project>> getAllProject(@Body ArrayList<Category> categories);

    @POST("project/filter")
    Call<ArrayList<Project>> getAllProject(@Body Integer idUser);

}
