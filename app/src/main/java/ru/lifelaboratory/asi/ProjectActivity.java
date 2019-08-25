package ru.lifelaboratory.asi;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.lifelaboratory.asi.adapter.ProjectAdapter;
import ru.lifelaboratory.asi.entity.CV;
import ru.lifelaboratory.asi.entity.Category;
import ru.lifelaboratory.asi.entity.Project;
import ru.lifelaboratory.asi.service.ProjectService;
import ru.lifelaboratory.asi.utils.Constants;

public class ProjectActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Project> projects = new ArrayList<>();
    ProjectAdapter projectAdapter;
    NavigationView navigationView;
    Dialog addProjectDialog;
    SharedPreferences memory;
    ProjectService projectService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_layout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SERVER_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        projectService = retrofit.create(ProjectService.class);
        //todo добавить фичу - фильтрация по категориям
        Call<List<Project>> getAllProject = projectService.getAllProject();
        getAllProject.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                projects.addAll(response.body());
                projectAdapter.notifyDataSetChanged();
                Log.d(Constants.LOG_TAG, "Найс запрос, проекты в хате");
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                Log.e(Constants.LOG_TAG, "ДЖИЗУС КРАЙЗИС, Проекты потеряны");
            }
        });

        projectAdapter = new ProjectAdapter(this, projects);

        ListView projectList = (ListView) findViewById(R.id.projectList);
        projectList.setAdapter(projectAdapter);

        ((FloatingActionButton) findViewById(R.id.btn_add_project)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProjectDialog = new Dialog(ProjectActivity.this);
                addProjectDialog.setTitle("Добавление резюме");
                addProjectDialog.setContentView(R.layout.dialog_add_project);

                addProjectDialog.show();

                ((Button) addProjectDialog.findViewById(R.id.btn_dialog_cancel)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addProjectDialog.cancel();
                    }
                });

                ((Button) addProjectDialog.findViewById(R.id.btn_dialog_add)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        memory = ProjectActivity.this.getSharedPreferences(Constants.MEMORY, MODE_PRIVATE);
                        final Integer userId = memory.getInt(Constants.USER_ID, 0);
                        final Project project = new Project();
                        EditText projectTitle = addProjectDialog.findViewById(R.id.project_title);
                        EditText projectDescription = addProjectDialog.findViewById(R.id.project_description);
                        EditText projectBudget = addProjectDialog.findViewById(R.id.project_budget);
                        project.setTitle(projectTitle.getText().toString());
                        project.setDescription(projectDescription.getText().toString());
                        project.setBudget(Integer.valueOf(projectBudget.getText().toString()));
                        project.setIdAuthor(userId);
                        Log.e(Constants.LOG_TAG, project.toString());

                        Call<Object> addProject = projectService.addProject(project);
                        addProject.enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                addProjectDialog.cancel();
                                Call<ArrayList<Project>> cvServiceAllCV = projectService.getAllProject(new ArrayList<Category>());
                                cvServiceAllCV.enqueue(new Callback<ArrayList<Project>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<Project>> call, Response<ArrayList<Project>> response) {
                                        if (response.body().size() > 0) {
                                            projects.clear();
                                            projects.addAll(response.body());
                                            projectAdapter.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ArrayList<Project>> call, Throwable t) {
                                        Log.e(Constants.LOG_TAG, "ServicesActivity error: " + t.getMessage());
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
                startActivity(new Intent(ProjectActivity.this, LessonListActivity.class));
                break;
            case R.id.nav_services:
                startActivity(new Intent(ProjectActivity.this, ServicesActivity.class));
                break;
            case R.id.nav_people:
                startActivity(new Intent(ProjectActivity.this, PeoplesActivity.class));
                break;
            case R.id.nav_profile:
                startActivity(new Intent(ProjectActivity.this, ProfileActivity.class));
                break;
            case R.id.nav_project:
                startActivity(new Intent(ProjectActivity.this, ProjectActivity.class));
                break;
            case R.id.nav_exit:
                startActivity(new Intent(ProjectActivity.this, MainActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
