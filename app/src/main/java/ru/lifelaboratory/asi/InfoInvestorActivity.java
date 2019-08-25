package ru.lifelaboratory.asi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.lifelaboratory.asi.adapter.HistoryBudgetAdapter;
import ru.lifelaboratory.asi.entity.Budget;
import ru.lifelaboratory.asi.entity.Category;
import ru.lifelaboratory.asi.entity.HistoryBudget;
import ru.lifelaboratory.asi.entity.User;
import ru.lifelaboratory.asi.service.UserService;
import ru.lifelaboratory.asi.utils.Constants;

public class InfoInvestorActivity extends Activity {

    HistoryBudgetAdapter historyBudgetAdapter;
    SharedPreferences memory;

    ImageView photo ;
    TextView description;
    TextView categorys;
    TextView rate;
    TextView budget;
    ListView invest;

    ArrayList<HistoryBudget> listOfBudget = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.investor_info_layout);

        photo = (ImageView) findViewById(R.id.logo);
        description = (TextView) findViewById(R.id.description);
        categorys = (TextView) findViewById(R.id.category);
        rate = (TextView) findViewById(R.id.rate);
        budget = (TextView) findViewById(R.id.budget);
        invest = (ListView) findViewById(R.id.invest);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SERVER_URL).
                addConverterFactory(GsonConverterFactory.create()).build();

        ((ImageView) findViewById(R.id.btn_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InfoInvestorActivity.this, InvestorsActivity.class));
            }
        });

        final UserService userService = retrofit.create(UserService.class);
        memory = getSharedPreferences(Constants.MEMORY, MODE_PRIVATE);
        Integer idUser = memory.getInt(Constants.INVESTOR_ID, 0);

        Call<User> user = userService.profile(idUser);
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Picasso.with(InfoInvestorActivity.this)
                        .load(response.body().getPhotoUrl())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(photo);
                description.setText(response.body().getDescription());
                rate.setText("Рейтинг: " + String.format("%.2f", response.body().getRate()));
                budget.setText("Готовность инвестировать: " + String.format("%.3f",response.body().getBudget()));
                Log.e(Constants.LOG_TAG, "Все ок");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(Constants.LOG_TAG, "Фиксики где вы блять");
            }
        });
        Call<ArrayList<Category>> userCategories = userService.getCategory(idUser);
        userCategories.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                StringBuilder stringBuilder = new StringBuilder();
                ArrayList<Category> categories = new ArrayList<>();
                categories.addAll(response.body());
                for (int index = 0; index < categories.size(); index++) {
                    if (index != 0) {
                        stringBuilder.append("/");
                    }
                    stringBuilder.append(categories.get(index).getTitle());
                }
                categorys.setText(stringBuilder.toString());
                Log.e(Constants.LOG_TAG, "Категории ок");
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                Log.e(Constants.LOG_TAG, "Категории не ок");
            }
        });

        Call<ArrayList<HistoryBudget>> invistitions = userService.getInvestitions(idUser);
        invistitions.enqueue(new Callback<ArrayList<HistoryBudget>>() {
            @Override
            public void onResponse(Call<ArrayList<HistoryBudget>> call, Response<ArrayList<HistoryBudget>> response) {
                listOfBudget.addAll(response.body());
                historyBudgetAdapter.notifyDataSetChanged();
                Log.d(Constants.LOG_TAG, "Инвестиции ок");
            }

            @Override
            public void onFailure(Call<ArrayList<HistoryBudget>> call, Throwable t) {
                Log.e(Constants.LOG_TAG, "Инвестиции не ок");
            }
        });

        historyBudgetAdapter = new HistoryBudgetAdapter(this, listOfBudget);
        invest.setAdapter(historyBudgetAdapter);
    }
}
