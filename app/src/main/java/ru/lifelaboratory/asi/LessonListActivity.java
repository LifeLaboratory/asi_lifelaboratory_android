package ru.lifelaboratory.asi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.lifelaboratory.asi.adapter.LessonAdapter;
import ru.lifelaboratory.asi.entity.Document;
import ru.lifelaboratory.asi.entity.StatusSignIn;
import ru.lifelaboratory.asi.service.LessonService;
import ru.lifelaboratory.asi.utils.Constants;

public class LessonListActivity extends AppCompatActivity {
    List<Document> items = new ArrayList<>();
    LessonAdapter lessonAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_list);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SERVER_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        LessonService lessonService = retrofit.create(LessonService.class);
        Call<List<Document>> lessons = lessonService.allLessons();
        lessons.enqueue(new Callback<List<Document>>() {
            @Override
            public void onResponse(Call<List<Document>> call, Response<List<Document>> response) {
                if (response.body() != null) {
                    Log.e(Constants.LOG_TAG, response.body().toString());
                    items.addAll(response.body());
                } else {
                    Log.e(Constants.LOG_TAG, response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Document>> call, Throwable t) {
                Log.e(Constants.LOG_TAG, "LessonsActivity error: " + t.getMessage());
            }
        });

        lessonAdapter = new LessonAdapter(this, items);
        ListView listView = findViewById(R.id.lvlesson);
        listView.setAdapter(lessonAdapter);

    }
}
