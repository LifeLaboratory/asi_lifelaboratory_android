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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.lifelaboratory.asi.adapter.ServicesAdapter;
import ru.lifelaboratory.asi.entity.Service;
import ru.lifelaboratory.asi.service.ServicesService;
import ru.lifelaboratory.asi.utils.Constants;

public class ServicesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    ServicesAdapter servicesAdapter;
    List<Service> services = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ListView listService = (ListView) findViewById(R.id.list_services);
        services = new ArrayList<>();
        servicesAdapter = new ServicesAdapter(this, services);
        listService.setAdapter(servicesAdapter);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SERVER_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        ServicesService servicesService = retrofit.create(ServicesService.class);
        Call<List<Service>> serviceCall = servicesService.get();
        serviceCall.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                Log.e(Constants.LOG_TAG, response.body().toString());
                services.clear();
                services.addAll(response.body());
                servicesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {
                Log.e(Constants.LOG_TAG, "ServicesActivity error: " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_profile:
                startActivity(new Intent(ServicesActivity.this, ProfileActivity.class));
                break;
            case R.id.nav_services:
                startActivity(new Intent(ServicesActivity.this, ServicesActivity.class));
                break;
            case R.id.nav_docs:
                startActivity(new Intent(ServicesActivity.this, LessonListActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
