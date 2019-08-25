package ru.lifelaboratory.asi.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.lifelaboratory.asi.entity.Document;
import ru.lifelaboratory.asi.entity.Lesson;

public interface LessonService {
    @GET("/lessons")
    Call<List<Document>> allLessons();

    @GET("/lesson/{id}")
    Call<Document> thislesson(@Path("id") Integer id);

    @POST("/lesson")
    Call<Object> create(@Body Lesson lesson);

}
