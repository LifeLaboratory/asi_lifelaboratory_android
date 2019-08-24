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
import ru.lifelaboratory.asi.adapter.DocumentAdapter;
import ru.lifelaboratory.asi.entity.Document;
import ru.lifelaboratory.asi.entity.FilterForDocument;
import ru.lifelaboratory.asi.entity.Project;
import ru.lifelaboratory.asi.entity.User;
import ru.lifelaboratory.asi.service.DocumentService;
import ru.lifelaboratory.asi.service.ProjectService;
import ru.lifelaboratory.asi.service.UserService;
import ru.lifelaboratory.asi.utils.Constants;

public class InfoProjectActivity extends Activity {

    DocumentAdapter documentAdapter;
    SharedPreferences memory;

    ImageView photo ;
    TextView description;
    TextView budget;
    TextView infoAuthor;
    ListView listDoc;

    ArrayList<Document> listOfDocument = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_project_layout);

        photo = (ImageView) findViewById(R.id.photo);
        description = (TextView) findViewById(R.id.description);
        budget = (TextView) findViewById(R.id.budget);
        infoAuthor = (TextView) findViewById(R.id.infoAuthor);
        listDoc = (ListView) findViewById(R.id.listDoc);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SERVER_URL).
                addConverterFactory(GsonConverterFactory.create()).build();

        ProjectService projectService = retrofit.create(ProjectService.class);
        final UserService userService = retrofit.create(UserService.class);
        final DocumentService documentService = retrofit.create(DocumentService.class);

        memory = getSharedPreferences(Constants.MEMORY, MODE_PRIVATE);
        final Integer idProject = memory.getInt(Constants.PROJECT_ID, 0);
        Call<Project> projectCall = projectService.getProject(idProject);

        projectCall.enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                Log.d(Constants.LOG_TAG, "ВСЕМ СТОЯТЕ ЦЕ ПРОЕКТ");
                Picasso.with(InfoProjectActivity.this)
                        .load(response.body().getPhoto())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(photo);
                description.setText(response.body().getDescription());
                budget.setText(String.format("%f", response.body().getBudget()));
                Call<User> user = userService.profile(response.body().getId());
                user.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d(Constants.LOG_TAG, "Гуд, мы нашли usera");
                        infoAuthor.setText(response.body().getName());
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e(Constants.LOG_TAG, "Мир соснул, а юзер сдох");
                    }
                });
                Call<ArrayList<Document>> documents = documentService.getDocumentsById(new FilterForDocument(0, idProject, 0));
                documents.enqueue(new Callback<ArrayList<Document>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Document>> call, Response<ArrayList<Document>> response) {
                        Log.d(Constants.LOG_TAG, "Доки у нас заводи мотор");
                        listOfDocument.addAll(response.body());
                        documentAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Document>> call, Throwable t) {
                        Log.e(Constants.LOG_TAG, "Гномы все СПИ%#&ЛИ");
                    }
                });

                documentAdapter = new DocumentAdapter(InfoProjectActivity.this, listOfDocument);
                listDoc.setAdapter(documentAdapter);
            }

            @Override
            public void onFailure(Call<Project> call, Throwable t) {
                Log.e(Constants.LOG_TAG, "Помянем проект, ушел в другой мир");
            }
        });
    }
}
