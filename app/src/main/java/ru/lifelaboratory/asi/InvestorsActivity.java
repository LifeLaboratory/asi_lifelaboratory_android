package ru.lifelaboratory.asi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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

public class InvestorsActivity extends Activity {

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
}
