package ru.lifelaboratory.asi.service;

import android.app.ListActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.lifelaboratory.asi.entity.Document;

public interface LessonService {
    @GET("lessons")
    Call<List<Document>> allLessons();

    @GET("lesson/")
    Call<Document> thislesson(Integer id);
}
