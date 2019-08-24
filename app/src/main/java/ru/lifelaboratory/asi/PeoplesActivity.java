package ru.lifelaboratory.asi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
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
        UserService cvService = retrofit.create(UserService.class);
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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_profile:
                startActivity(new Intent(PeoplesActivity.this, ProfileActivity.class));
                break;
            case R.id.nav_services:
                startActivity(new Intent(PeoplesActivity.this, ServicesActivity.class));
                break;
            case R.id.nav_docs:
                startActivity(new Intent(PeoplesActivity.this, LessonListActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
