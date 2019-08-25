package ru.lifelaboratory.asi;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.jaedongchicken.ytplayer.YoutubePlayerView;
import com.jaedongchicken.ytplayer.model.YTParams;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.lifelaboratory.asi.entity.Document;
import ru.lifelaboratory.asi.entity.Lesson;
import ru.lifelaboratory.asi.service.LessonService;
import ru.lifelaboratory.asi.utils.Constants;


public class LessonActivity extends AppCompatActivity {
    JcPlayerView jcPlayerView = null;
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ((ImageView) findViewById(R.id.btn_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LessonActivity.this, LessonListActivity.class));
            }
        });
        final LinearLayout linLayout = findViewById(R.id.mainLayout);
        //final LinearLayout linLayout = new LinearLayout(this);
        //linLayout.setOrientation(LinearLayout.VERTICAL);
        //LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        //setContentView(linLayout, linLayoutParam);
        if(getIntent().getIntExtra("DOC_ID", -1)!= -1){

            Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SERVER_URL).
                    addConverterFactory(GsonConverterFactory.create()).build();
            LessonService lessonService = retrofit.create(LessonService.class);
            final Call<Document> lesson = lessonService.thislesson((Integer) getIntent().getIntExtra("DOC_ID", -1));
            lesson.enqueue(new Callback<Document>() {
                @Override
                public void onResponse(Call<Document> call, Response<Document> response) {
                    if (response.body() != null){
                        Log.e(Constants.LOG_TAG, response.body().toString());
                        type = response.body().getType();
                        switch (type) {
                            case 0: {
                                TextView lessonName = new TextView(LessonActivity.this);
                                lessonName.setText(response.body().getTitle());
                                LinearLayout.LayoutParams lessonNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lessonName.setLayoutParams(lessonNameParams);
                                linLayout.addView(lessonName);

                                ScrollView scrollView = new ScrollView(LessonActivity.this);
                                scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                linLayout.addView(scrollView);

                                LinearLayout tempLayout = new LinearLayout(LessonActivity.this);
                                tempLayout.setOrientation(LinearLayout.VERTICAL);
                                tempLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                scrollView.addView(tempLayout);

                                ImageView logo = new ImageView(LessonActivity.this);
                                Picasso.with(LessonActivity.this)
                                        .load(response.body().getPhoto())
                                        .placeholder(R.drawable.ic_launcher_foreground)
                                        .error(R.drawable.ic_launcher_foreground)
                                        .into(logo);
                                logo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                tempLayout.addView(logo);

                                TextView lessonText = new TextView(LessonActivity.this);
                                lessonText.setText(response.body().getDescription());
                                LinearLayout.LayoutParams lessonTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lessonName.setLayoutParams(lessonTextParams);
                                tempLayout.addView(lessonText);
                                break;
                            }
                            case 1: {
                                TextView lessonName = new TextView(LessonActivity.this);
                                lessonName.setText(response.body().getTitle());
                                LinearLayout.LayoutParams lessonNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lessonName.setLayoutParams(lessonNameParams);
                                linLayout.addView(lessonName);

                                YoutubePlayerView youtubePlayerView = new YoutubePlayerView(LessonActivity.this);
                                youtubePlayerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                linLayout.addView(youtubePlayerView);
                                YTParams params = new YTParams();

                                youtubePlayerView.setAutoPlayerHeight(LessonActivity.this);

                                youtubePlayerView.initialize(response.body().getUrl(), new YoutubePlayerView.YouTubeListener() {
                                    @Override
                                    public void onReady() {

                                    }

                                    @Override
                                    public void onStateChange(YoutubePlayerView.STATE state) {

                                    }

                                    @Override
                                    public void onPlaybackQualityChange(String arg) {

                                    }

                                    @Override
                                    public void onPlaybackRateChange(String arg) {

                                    }

                                    @Override
                                    public void onError(String arg) {

                                    }

                                    @Override
                                    public void onApiChange(String arg) {

                                    }

                                    @Override
                                    public void onCurrentSecond(double second) {

                                    }

                                    @Override
                                    public void onDuration(double duration) {

                                    }

                                    @Override
                                    public void logs(String log) {

                                    }

                                });
                                youtubePlayerView.play();

                                TextView lessonText = new TextView(LessonActivity.this);
                                lessonText.setText(response.body().getDescription());
                                LinearLayout.LayoutParams lessonTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lessonName.setLayoutParams(lessonNameParams);
                                linLayout.addView(lessonText);

                                break;
                            }
                            case 2:{
                                TextView lessonName = new TextView(LessonActivity.this);
                                lessonName.setText(response.body().getTitle());
                                LinearLayout.LayoutParams lessonNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lessonName.setLayoutParams(lessonNameParams);
                                linLayout.addView(lessonName);

                                jcPlayerView = new JcPlayerView(LessonActivity.this);
                                jcPlayerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                ArrayList<JcAudio> jcAudios = new ArrayList<>();
                                jcAudios.add(JcAudio.createFromURL(response.body().getDescription(),response.body().getUrl()));
                                jcPlayerView.initPlaylist(jcAudios, null);

                                linLayout.addView(jcPlayerView);

                                TextView lessonText = new TextView(LessonActivity.this);
                                lessonText.setText(response.body().getDescription());
                                LinearLayout.LayoutParams lessonTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lessonName.setLayoutParams(lessonNameParams);
                                linLayout.addView(lessonText);

                                break;
                            }
                            case 3:{
                                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.body().getUrl()));
                                startActivity(myIntent);
                            }
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
    @Override
    protected void onPause() {
        super.onPause();
        if(type == 2)jcPlayerView.pause();
    }
}
