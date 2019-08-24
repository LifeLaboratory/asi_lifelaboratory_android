package ru.lifelaboratory.asi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.lifelaboratory.asi.adapter.LessonAdapter;
import ru.lifelaboratory.asi.entity.Document;
import ru.lifelaboratory.asi.service.LessonService;
import ru.lifelaboratory.asi.utils.Constants;

public class LessonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_lesson);
        final LinearLayout linLayout = new LinearLayout(this);
        linLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        // устанавливаем linLayout как корневой элемент экрана
        setContentView(linLayout, linLayoutParam);
        Bundle bundle = getIntent().getExtras();
        if(bundle.get("DOC_ID") != null){
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SERVER_URL).
                    addConverterFactory(GsonConverterFactory.create()).build();
            LessonService lessonService = retrofit.create(LessonService.class);
            Call<Document> lesson = lessonService.thislesson((Integer) bundle.get("DOC_ID"));
            lesson.enqueue(new Callback<Document>() {
                @Override
                public void onResponse(Call<Document> call, Response<Document> response) {
                    if (response.body() != null) {
                        Log.e(Constants.LOG_TAG, response.body().toString());
                        switch (response.body().getType()){
                            case 0:{
                                TextView lessonName = new TextView(LessonActivity.this);
                                lessonName.setText(response.body().getTitle());
                                LinearLayout.LayoutParams lessonNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lessonName.setLayoutParams(lessonNameParams);
                                linLayout.addView(lessonName);

                                ScrollView scrollView = new ScrollView(LessonActivity.this);
                                scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                linLayout.addView(scrollView);

                                ImageView logo = new ImageView(LessonActivity.this);
                                Picasso.with(LessonActivity.this)
                                        .load(response.body().getPhoto())
                                        .placeholder(R.drawable.ic_launcher_foreground)
                                        .error(R.drawable.ic_launcher_foreground)
                                        .into(logo);
                                logo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                scrollView.addView(logo);

                                TextView lessonText = new TextView(LessonActivity.this);
                                lessonText.setText(response.body().getDescription());
                                LinearLayout.LayoutParams lessonTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lessonName.setLayoutParams(lessonNameParams);
                                scrollView.addView(lessonName);
                                break;
                            }
                            /*case 1:{
                                TextView lessonName = new TextView(LessonActivity.this);
                                lessonName.setText(response.body().getTitle());
                                LinearLayout.LayoutParams lessonNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lessonName.setLayoutParams(lessonNameParams);
                                linLayout.addView(lessonName);

                                ScrollView scrollView = new ScrollView(LessonActivity.this);
                                scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                linLayout.addView(scrollView);

                                ImageView logo = new ImageView(LessonActivity.this);
                                Picasso.with(LessonActivity.this)
                                        .load(response.body().getPhoto())
                                        .placeholder(R.drawable.ic_launcher_foreground)
                                        .error(R.drawable.ic_launcher_foreground)
                                        .into(logo);
                                logo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                scrollView.addView(logo);

                                TextView lessonText = new TextView(LessonActivity.this);
                                lessonText.setText(response.body().getDescription());
                                LinearLayout.LayoutParams lessonTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lessonName.setLayoutParams(lessonNameParams);
                                scrollView.addView(lessonName);
                                break;
                            }*/
                        }
                    } else {
                        Log.e(Constants.LOG_TAG, response.body().toString());
                    }
                }

                @Override
                public void onFailure(Call<Document> call, Throwable t) {
                    Log.e(Constants.LOG_TAG, "LessonsActivity error: " + t.getMessage());
                }
            });
        }
    }
}
