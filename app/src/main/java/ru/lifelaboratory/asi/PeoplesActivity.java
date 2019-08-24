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
import ru.lifelaboratory.asi.adapter.PeoplesAdapter;
import ru.lifelaboratory.asi.entity.CV;
import ru.lifelaboratory.asi.service.UserService;
import ru.lifelaboratory.asi.utils.Constants;

public class PeoplesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    PeoplesAdapter cvAdapter;
    List<CV> cv = null;
    Dialog addCVDialog;
    UserService cvService;
    SharedPreferences memory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peoples);

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

        ListView listPeoples = (ListView) findViewById(R.id.list_peoples);
        cv = new ArrayList<>();
        cvAdapter = new PeoplesAdapter(this, cv);
        listPeoples.setAdapter(cvAdapter);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SERVER_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        cvService = retrofit.create(UserService.class);
        Call<List<CV>> cvServiceAllCV = cvService.getAllCV();
        cvServiceAllCV.enqueue(new Callback<List<CV>>() {
            @Override
            public void onResponse(Call<List<CV>> call, Response<List<CV>> response) {
                if (response.body().size() > 0) {
                    Log.e(Constants.LOG_TAG, response.body().toString());
                    cv.clear();
                    cv.addAll(response.body());
                    cvAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<CV>> call, Throwable t) {
                Log.e(Constants.LOG_TAG, "ServicesActivity error: " + t.getMessage());
            }
        });

        ((FloatingActionButton) findViewById(R.id.btn_add_cv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCVDialog = new Dialog(PeoplesActivity.this);
                addCVDialog.setTitle("Добавление резюме");
                addCVDialog.setContentView(R.layout.dialog_add_cv);

                addCVDialog.show();

                ((Button) addCVDialog.findViewById(R.id.btn_dialog_cancel)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addCVDialog.cancel();
                    }
                });

                ((Button) addCVDialog.findViewById(R.id.btn_dialog_add)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        memory = PeoplesActivity.this.getSharedPreferences(Constants.MEMORY, MODE_PRIVATE);
                        Integer userId = memory.getInt(Constants.USER_ID, 0);
                        CV cv = new CV();
                        EditText cvTitle = addCVDialog.findViewById(R.id.cv_title);
                        EditText cvDescription = addCVDialog.findViewById(R.id.cv_description);
                        EditText cvUrl = addCVDialog.findViewById(R.id.cv_url);
                        cv.setTitle(cvTitle.getText().toString());
                        cv.setDescription(cvDescription.getText().toString());
                        cv.setUrl(cvUrl.getText().toString());
                        cv.setIdUser(userId);
                        Log.e(Constants.LOG_TAG, cv.toString());

                        Call<Object> cvServiceAllCV = cvService.addCV(cv);
                        cvServiceAllCV.enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                addCVDialog.cancel();
                                Call<List<CV>> cvServiceAllCV = cvService.getAllCV();
                                cvServiceAllCV.enqueue(new Callback<List<CV>>() {
                                    @Override
                                    public void onResponse(Call<List<CV>> call, Response<List<CV>> response) {
                                        if (response.body().size() > 0) {
                                            Log.e(Constants.LOG_TAG, response.body().toString());
                                            PeoplesActivity.this.cv.clear();
                                            PeoplesActivity.this.cv.addAll(response.body());
                                            cvAdapter.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<CV>> call, Throwable t) {
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
                startActivity(new Intent(PeoplesActivity.this, LessonListActivity.class));
                break;
            case R.id.nav_services:
                startActivity(new Intent(PeoplesActivity.this, ServicesActivity.class));
                break;
            case R.id.nav_people:
                startActivity(new Intent(PeoplesActivity.this, PeoplesActivity.class));
                break;
            case R.id.nav_profile:
                startActivity(new Intent(PeoplesActivity.this, ProfileActivity.class));
                break;
            case R.id.nav_project:
                startActivity(new Intent(PeoplesActivity.this, ProjectActivity.class));
                break;
            case R.id.nav_exit:
                startActivity(new Intent(PeoplesActivity.this, MainActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
