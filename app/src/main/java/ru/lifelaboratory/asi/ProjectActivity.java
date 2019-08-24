package ru.lifelaboratory.asi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.lifelaboratory.asi.adapter.ProjectAdapter;
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
        ProjectService allProject = retrofit.create(ProjectService.class);

        projectAdapter = new ProjectAdapter(this, projects);

        ListView projectList = (ListView) findViewById(R.id.projectList);
        projectList.setAdapter(projectAdapter);
    }
}
