package ru.lifelaboratory.asi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.lifelaboratory.asi.entity.StatusSignUp;
import ru.lifelaboratory.asi.entity.User;
import ru.lifelaboratory.asi.service.UserService;
import ru.lifelaboratory.asi.utils.Constants;

public class SignUpActivity extends AppCompatActivity {

    EditText userLogin, userPassword, userRePassword, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        userLogin = (EditText) findViewById(R.id.user_login);
        userPassword = (EditText) findViewById(R.id.user_password);
        userRePassword = (EditText) findViewById(R.id.user_repassword);
        userName = (EditText) findViewById(R.id.user_name);

        ((ImageView) findViewById(R.id.btn_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            }
        });

        ((Button) findViewById(R.id.btn_signup)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!userPassword.getText().toString().equals(userRePassword.getText().toString())) {
                    Toast.makeText(SignUpActivity.this, "Пароли не сходятся", Toast.LENGTH_SHORT).show();
                    return;
                }

                Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SERVER_URL).
                        addConverterFactory(GsonConverterFactory.create()).build();
                UserService signUpService = retrofit.create(UserService.class);
                Call<StatusSignUp> signUpInfo = signUpService.register(new User(userName.getText().toString(), userLogin.getText().toString(), userPassword.getText().toString()));
                signUpInfo.enqueue(new Callback<StatusSignUp>() {
                    @Override
                    public void onResponse(Call<StatusSignUp> call, Response<StatusSignUp> response) {
                        if (response.body().getIdUser() != null) {
                            Toast.makeText(SignUpActivity.this, "Регистрация успешно завершена", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(SignUpActivity.this, "Ошибка регистрации", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusSignUp> call, Throwable t) {
                        Log.e(Constants.LOG_TAG, "SignUpActivity error: " + t.getMessage());
                    }
                });
            }
        });

    }
}
