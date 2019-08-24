package ru.lifelaboratory.asi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.lifelaboratory.asi.entity.StatusSignIn;
import ru.lifelaboratory.asi.entity.User;
import ru.lifelaboratory.asi.service.UserService;
import ru.lifelaboratory.asi.utils.Constants;

public class MainActivity extends AppCompatActivity {

    EditText userLogin, userPassword;
    SharedPreferences memory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        userLogin = (EditText) findViewById(R.id.user_login);
        userPassword = (EditText) findViewById(R.id.user_password);

        ((Button) findViewById(R.id.btn_signup)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

        ((Button) findViewById(R.id.btn_signin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SERVER_URL).
                        addConverterFactory(GsonConverterFactory.create()).build();
                UserService signInService = retrofit.create(UserService.class);
                Call<StatusSignIn> signInInfo = signInService.auth(new User(userLogin.getText().toString(), userPassword.getText().toString()));
                signInInfo.enqueue(new Callback<StatusSignIn>() {
                    @Override
                    public void onResponse(Call<StatusSignIn> call, Response<StatusSignIn> response) {
                        if (response.body().getIdUser() != null) {
                            Toast.makeText(MainActivity.this, "Авторизация успешно завершена", Toast.LENGTH_SHORT).show();
                            memory = getSharedPreferences(Constants.MEMORY, Context.MODE_PRIVATE);
                            memory.edit().putInt(Constants.USER_ID, response.body().getIdUser()).commit();
                            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        } else {
                            Log.e(Constants.LOG_TAG, response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusSignIn> call, Throwable t) {
                        Log.e(Constants.LOG_TAG, "MainActivity error: " + t.getMessage());
                    }
                });
            }
        });

    }
}
