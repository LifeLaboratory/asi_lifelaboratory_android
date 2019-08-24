package ru.lifelaboratory.asi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import ru.lifelaboratory.asi.adapter.LessonAdapter;
import ru.lifelaboratory.asi.entity.Document;

public class LessonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

    }
}
