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
import android.widget.ImageView;

import ru.lifelaboratory.asi.utils.Constants;

public class RegisterIPActivity extends Activity  implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ip);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        ((ImageView) findViewById(R.id.btn_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterIPActivity.this, LessonListActivity.class));
            }
        });

        ((FloatingActionButton) findViewById(R.id.btn_register_ip)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Constants.SERVER_URL + "/client/reg_ip.pdf"));
                RegisterIPActivity.this.startActivity(i);
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
