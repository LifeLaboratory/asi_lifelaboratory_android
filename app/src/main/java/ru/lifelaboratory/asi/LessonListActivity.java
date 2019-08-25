package ru.lifelaboratory.asi;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.lifelaboratory.asi.adapter.LessonAdapter;
import ru.lifelaboratory.asi.entity.Document;
import ru.lifelaboratory.asi.entity.Lesson;
import ru.lifelaboratory.asi.service.LessonService;
import ru.lifelaboratory.asi.utils.Constants;

public class LessonListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    List<Document> items = new ArrayList<>();
    LessonAdapter lessonAdapter;
    Dialog addLessonDialog;
    SharedPreferences memory;
    LessonService lessonService;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SERVER_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        lessonService = retrofit.create(LessonService.class);
        Call<List<Document>> lessons = lessonService.allLessons();
        lessons.enqueue(new Callback<List<Document>>() {
            @Override
            public void onResponse(Call<List<Document>> call, Response<List<Document>> response) {
                if (response.body() != null) {
                    items.clear();
                    items.addAll(response.body());
                    lessonAdapter.notifyDataSetChanged();
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

        ((FloatingActionButton) findViewById(R.id.btn_add_lesson)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLessonDialog = new Dialog(LessonListActivity.this);
                addLessonDialog.setTitle("Добавление урока");
                addLessonDialog.setContentView(R.layout.dialog_add_lesson);

                addLessonDialog.show();

                ((Button) addLessonDialog.findViewById(R.id.btn_dialog_cancel)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addLessonDialog.cancel();
                    }
                });

                ((Button) addLessonDialog.findViewById(R.id.btn_dialog_add)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        memory = LessonListActivity.this.getSharedPreferences(Constants.MEMORY, MODE_PRIVATE);
                        final Integer userId = memory.getInt(Constants.USER_ID, 0);
                        final Lesson lesson = new Lesson();
                        EditText lessonTitle = addLessonDialog.findViewById(R.id.lesson_title);
                        EditText lessonUrl = addLessonDialog.findViewById(R.id.lesson_url);
                        RadioGroup lessonType = addLessonDialog.findViewById(R.id.lesson_type);
                        lesson.setTitle(lessonTitle.getText().toString());
                        lesson.setUrl(lessonUrl.getText().toString());
                        switch (lessonType.getCheckedRadioButtonId()) {
                            case R.id.lesson_type_audio: {
                                lesson.setType(2);
                            } break;
                            case R.id.lesson_type_text: {
                                lesson.setType(0);
                            } break;
                            case R.id.lesson_type_video: {
                                lesson.setType(1);
                            } break;
                            case R.id.lesson_type_url: {
                                lesson.setType(3);
                            } break;
                        }

                        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SERVER_URL).
                                addConverterFactory(GsonConverterFactory.create()).build();
                        lessonService = retrofit.create(LessonService.class);
                        Call<Object> addLesson = lessonService.create(lesson);
                        addLesson.enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                addLessonDialog.cancel();
                                Call<List<Document>> lessons = lessonService.allLessons();
                                lessons.enqueue(new Callback<List<Document>>() {
                                    @Override
                                    public void onResponse(Call<List<Document>> call, Response<List<Document>> response) {
                                        if (response.body() != null) {
                                            items.clear();
                                            items.addAll(response.body());
                                            lessonAdapter.notifyDataSetChanged();
                                        } else {
                                            Log.e(Constants.LOG_TAG, response.body().toString());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<Document>> call, Throwable t) {
                                        Log.e(Constants.LOG_TAG, "LessonsActivity error: " + t.getMessage());
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {
                                Log.e(Constants.LOG_TAG, "PeoplesActivity error: " + t.getMessage());
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_docs:
                startActivity(new Intent(LessonListActivity.this, LessonListActivity.class));
                break;
            case R.id.nav_services:
                startActivity(new Intent(LessonListActivity.this, ServicesActivity.class));
                break;
            case R.id.nav_people:
                startActivity(new Intent(LessonListActivity.this, PeoplesActivity.class));
                break;
            case R.id.nav_project:
                startActivity(new Intent(LessonListActivity.this, ProjectActivity.class));
                break;
            case R.id.nav_exit:
                startActivity(new Intent(LessonListActivity.this, MainActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
