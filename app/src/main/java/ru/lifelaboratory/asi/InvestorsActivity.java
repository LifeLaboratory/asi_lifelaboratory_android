package ru.lifelaboratory.asi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
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

public class InvestorsActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    InvestorAdapter investorAdapter;
    ArrayList<User> investors = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor);

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
            case R.id.nav_project:
                startActivity(new Intent(InvestorsActivity.this, ProjectActivity.class));
                break;
            case R.id.nav_exit:
                startActivity(new Intent(InvestorsActivity.this, MainActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
