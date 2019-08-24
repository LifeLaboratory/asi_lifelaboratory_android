package ru.lifelaboratory.asi.service;

import android.app.ListActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.lifelaboratory.asi.entity.Document;

public interface LessonService {
    @GET("/lessons")
    Call<List<Document>> allLessons();

    @GET("/lesson/{id}")
    Call<Document> thislesson(@Path("id") Integer id);
}
