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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.lifelaboratory.asi.adapter.InvestorAdapter;
import ru.lifelaboratory.asi.entity.User;
import ru.lifelaboratory.asi.service.UserService;
import ru.lifelaboratory.asi.utils.Constants;

public class InvestorsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    InvestorAdapter investorAdapter;
    ArrayList<User> investors = new ArrayList<>();
    NavigationView navigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor);

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

        UserService userService = retrofit.create(UserService.class);
        Call<ArrayList<User>> getInvestors = userService.getInvestors();
        getInvestors.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                Log.d(Constants.LOG_TAG, "РАБОТАЕТ");
                investors.addAll(response.body());
                investorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Log.e(Constants.LOG_TAG, "Фиксики на выход блэт");
            }
        });
        investorAdapter = new InvestorAdapter(this, investors);

        ListView investorList = (ListView) findViewById(R.id.investors);
        investorList.setAdapter(investorAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_docs:
                startActivity(new Intent(InvestorsActivity.this, LessonListActivity.class));
                break;
            case R.id.nav_services:
                startActivity(new Intent(InvestorsActivity.this, ServicesActivity.class));
                break;
            case R.id.nav_people:
                startActivity(new Intent(InvestorsActivity.this, PeoplesActivity.class));
                break;
            case R.id.nav_profile:
                startActivity(new Intent(InvestorsActivity.this, ProfileActivity.class));
                break;
            case R.id.nav_project:
                startActivity(new Intent(InvestorsActivity.this, ProjectActivity.class));
                break;
            case R.id.nav_exit:
                startActivity(new Intent(InvestorsActivity.this, MainActivity.class));
                break;
            case R.id.nav_investors:
                startActivity(new Intent(InvestorsActivity.this, InvestorsActivity.class));
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
