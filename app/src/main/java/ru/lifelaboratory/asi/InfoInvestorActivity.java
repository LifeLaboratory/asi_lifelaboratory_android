package ru.lifelaboratory.asi;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.lifelaboratory.asi.entity.Budget;
import ru.lifelaboratory.asi.entity.Category;
import ru.lifelaboratory.asi.entity.User;
import ru.lifelaboratory.asi.service.UserService;
import ru.lifelaboratory.asi.utils.Constants;

public class InfoInvestorActivity extends Activity {

    SharedPreferences memory;

    ImageView photo ;
    TextView description;
    TextView categorys;
    TextView rate;
    TextView budget;
    ListView invest;

    ArrayList<Budget> listOfBudget = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.investor_info_layout);

        photo = (ImageView) findViewById(R.id.logo);
        description = (TextView) findViewById(R.id.description);
        categorys = (TextView) findViewById(R.id.category);
        rate = (TextView) findViewById(R.id.rate);
        budget = (TextView) findViewById(R.id.budget);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SERVER_URL).
                addConverterFactory(GsonConverterFactory.create()).build();

        final UserService userService = retrofit.create(UserService.class);
        memory = getSharedPreferences(Constants.MEMORY, MODE_PRIVATE);
        Integer idUser = memory.getInt(Constants.USER_ID, 0);

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
                rate.setText(String.format("%f", response.body().getRate()));
                StringBuilder stringBuilder = new StringBuilder();
                ArrayList<Category> categories = response.body().getCategories();
                for (int index = 0; index < categories.size(); index++) {
                    if (index != 0) {
                        stringBuilder.append("/");
                    }
                    stringBuilder.append(categories.get(index));
                }
                categorys.setText(stringBuilder.toString());
                Log.e(Constants.LOG_TAG, "Все ок");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(Constants.LOG_TAG, "Фиксики где вы блять");
            }
        });
    }
}
