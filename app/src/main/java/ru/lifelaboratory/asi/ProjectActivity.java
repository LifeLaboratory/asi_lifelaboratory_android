package ru.lifelaboratory.asi;

import android.app.Activity;
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
import ru.lifelaboratory.asi.adapter.ProjectAdapter;
import ru.lifelaboratory.asi.entity.Category;
import ru.lifelaboratory.asi.entity.Project;
import ru.lifelaboratory.asi.service.ProjectService;
import ru.lifelaboratory.asi.utils.Constants;

public class ProjectActivity extends Activity {

    ArrayList<Project> projects = new ArrayList<>();
    ProjectAdapter projectAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_layout);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SERVER_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        ProjectService projectService = retrofit.create(ProjectService.class);
        //todo добавить фичу - фильтрация по категориям
        Call<ArrayList<Project>> getAllProject = projectService.getAllProject(new ArrayList<Category>());
        getAllProject.enqueue(new Callback<ArrayList<Project>>() {
            @Override
            public void onResponse(Call<ArrayList<Project>> call, Response<ArrayList<Project>> response) {
                projects.addAll(response.body());
                Log.d(Constants.LOG_TAG, "Найс запрос, проекты в хате");
            }

            @Override
            public void onFailure(Call<ArrayList<Project>> call, Throwable t) {
                Log.e(Constants.LOG_TAG, "ДЖИЗУС КРАЙЗИС, Проекты потеряны");
            }
        });

        projectAdapter = new ProjectAdapter(this, projects);

        ListView projectList = (ListView) findViewById(R.id.projectList);
        projectList.setAdapter(projectAdapter);
    }
}
