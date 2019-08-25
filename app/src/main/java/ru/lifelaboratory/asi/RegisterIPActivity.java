package ru.lifelaboratory.asi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.lifelaboratory.asi.entity.IPUser;
import ru.lifelaboratory.asi.service.IPService;
import ru.lifelaboratory.asi.utils.Constants;

public class RegisterIPActivity extends Activity  implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences memory;
    IPService ipService;
    IPUser ipUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ip);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        ((ImageView) findViewById(R.id.btn_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterIPActivity.this, ProfileActivity.class));
            }
        });

        ((FloatingActionButton) findViewById(R.id.btn_register_ip)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ipUser = new IPUser();
                memory = RegisterIPActivity.this.getSharedPreferences(Constants.MEMORY, MODE_PRIVATE);
                final Integer userId = memory.getInt(Constants.USER_ID, 0);
                ipUser.setId(userId);

                String inn = ((EditText) findViewById(R.id.user_inn)).getText().toString();
                String birthday = ((EditText) findViewById(R.id.user_birthday)).getText().toString();
                String birthdayPlace = ((EditText) findViewById(R.id.user_birthday_place)).getText().toString();
                String citizenship = ((EditText) findViewById(R.id.user_citizenship)).getText().toString();
                String countryCode = ((EditText) findViewById(R.id.user_country_code)).getText().toString();
                String index = ((EditText) findViewById(R.id.user_index)).getText().toString();
                String subject = ((EditText) findViewById(R.id.user_subject)).getText().toString();
                String areaName = ((EditText) findViewById(R.id.user_area_name)).getText().toString();
                String typeCity = ((EditText) findViewById(R.id.user_type_city)).getText().toString();
                String cityName = ((EditText) findViewById(R.id.user_city_name)).getText().toString();
                String street = ((EditText) findViewById(R.id.user_street)).getText().toString();
                String house = ((EditText) findViewById(R.id.user_house)).getText().toString();
                String block = ((EditText) findViewById(R.id.user_block)).getText().toString();
                String app = ((EditText) findViewById(R.id.user_app)).getText().toString();
                String typeDoc = ((EditText) findViewById(R.id.user_type_doc)).getText().toString();
                String dateDoc = ((EditText) findViewById(R.id.user_date_doc)).getText().toString();
                String whoDoc = ((EditText) findViewById(R.id.user_who_doc)).getText().toString();
                ipUser.setInn(inn);
                ipUser.setBirthday(birthday);
                ipUser.setBirthdayPlace(birthdayPlace);
                ipUser.setCitizenship(citizenship);
                ipUser.setCountryCode(countryCode);
                ipUser.setIndex(index);
                ipUser.setSubject(subject);
                ipUser.setAreaName(areaName);
                ipUser.setTypeCity(typeCity);
                ipUser.setCityName(cityName);
                ipUser.setStreet(street);
                ipUser.setHouse(house);
                ipUser.setBlock(block);
                ipUser.setApp(app);
                ipUser.setTypeDoc(typeDoc);
                ipUser.setDateDoc(dateDoc);
                ipUser.setWhoDoc(whoDoc);

                Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SERVER_URL).
                        addConverterFactory(GsonConverterFactory.create()).build();
                ipService = retrofit.create(IPService.class);
                Call<Object> generate = ipService.generate(ipUser);
                generate.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Call<Object> ipServiceCall = ipService.register(ipUser);
                        ipServiceCall.enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setDataAndType(Uri.parse(Constants.SERVER_URL + "print_form/read/" + userId + ".pdf"), "application/pdf");
                                RegisterIPActivity.this.startActivity(i);
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {
                                Log.e(Constants.LOG_TAG, "SignUpActivity error: " + t.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.e(Constants.LOG_TAG, "SignUpActivity error: " + t.getMessage());
                    }
                });
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_docs:
                startActivity(new Intent(RegisterIPActivity.this, LessonListActivity.class));
                break;
            case R.id.nav_services:
                startActivity(new Intent(RegisterIPActivity.this, ServicesActivity.class));
                break;
            case R.id.nav_people:
                startActivity(new Intent(RegisterIPActivity.this, PeoplesActivity.class));
                break;
            case R.id.nav_project:
                startActivity(new Intent(RegisterIPActivity.this, ProjectActivity.class));
                break;
            case R.id.nav_exit:
                startActivity(new Intent(RegisterIPActivity.this, MainActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
