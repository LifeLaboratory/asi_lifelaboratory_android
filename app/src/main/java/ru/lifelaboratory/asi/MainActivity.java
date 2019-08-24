package ru.lifelaboratory.asi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ((TextView) findViewById(R.id.to_link)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLink = new Intent(MainActivity.this, LinkActivity.class);
                startActivity(toLink);
            }
        });

    }
}
