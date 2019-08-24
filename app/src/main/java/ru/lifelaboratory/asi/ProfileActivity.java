package ru.lifelaboratory.asi;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.lifelaboratory.asi.entity.User;
import ru.lifelaboratory.asi.service.UserService;
import ru.lifelaboratory.asi.utils.Constants;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences memory;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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

        memory = getSharedPreferences(Constants.MEMORY, MODE_PRIVATE);
        Integer userId = memory.getInt(Constants.USER_ID, 0);
        Log.e(Constants.LOG_TAG, userId.toString());
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SERVER_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        UserService profileService = retrofit.create(UserService.class);
        Call<User> profileInfo = profileService.profile(userId);
        profileInfo.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e(Constants.LOG_TAG, response.body().toString());
                User tmpUser = response.body();
                if (tmpUser != null) {
                    ((TextView) findViewById(R.id.user_name)).setText(tmpUser.getName() == null ? "Anonymous" : tmpUser.getName());
                    ((TextView) findViewById(R.id.user_rate)).setText("Рейтинг: " + tmpUser.getRate());
                    ((TextView) findViewById(R.id.user_description)).setText(tmpUser.getDescription() == null ? "Нет описания" : tmpUser.getDescription());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(Constants.LOG_TAG, "ProfileActivity error: " + t.getMessage());
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_docs:
                startActivity(new Intent(ProfileActivity.this, LessonListActivity.class));
                break;
            case R.id.nav_services:
                startActivity(new Intent(ProfileActivity.this, ServicesActivity.class));
                break;
            case R.id.nav_people:
                startActivity(new Intent(ProfileActivity.this, PeoplesActivity.class));
                break;
            case R.id.nav_project:
                startActivity(new Intent(ProfileActivity.this, ProjectActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
