package ru.lifelaboratory.asi.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.lifelaboratory.asi.InfoProjectActivity;
import ru.lifelaboratory.asi.R;
import ru.lifelaboratory.asi.entity.Project;
import ru.lifelaboratory.asi.utils.Constants;

import static android.content.Context.MODE_PRIVATE;

public class ProjectAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Project> projects;
    SharedPreferences memory;

    public ProjectAdapter(Context ctx,
                          ArrayList<Project> projects) {
        this.ctx = ctx;
        this.lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.projects = projects;
    }


    @Override
    public int getCount() {
        return projects.size();
    }

    @Override
    public Object getItem(int position) {
        return projects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.project_item, parent, false);
        }

        final Project project = (Project) getItem(position);

        ImageView logo = (ImageView) view.findViewById(R.id.logo);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView author = (TextView) view.findViewById(R.id.author);
        TextView category = (TextView) view.findViewById(R.id.category);
        TextView description = (TextView) view.findViewById(R.id.description);
        TextView budget = (TextView) view.findViewById(R.id.budget);
        TextView rate = (TextView) view.findViewById(R.id.rate);
        FloatingActionButton info = (FloatingActionButton) view.findViewById(R.id.info);

        Picasso.with(this.ctx)
                .load(project.getPhoto())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(logo);
        title.setText(project.getTitle());
        //TODO сделать запрос на возвращение объекта пользователя
//        author.setText(project.getIdAuthor());
        description.setText(project.getDescription());
        budget.setText(String.format("%f", project.getBudget()));
        rate.setText(String.format("%f", project.getRate()));

        /*ArrayList<Category> categories = (ArrayList<Category>) project.getCategories();
        StringBuilder stringBuilder = new StringBuilder();
        for (int index = 0; index < categories.size(); index++) {
            if (index != 0) {
                stringBuilder.append("/");
            }
            stringBuilder.append(categories.get(index).getTitle());
        }
        category.setText(stringBuilder.toString());*/

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memory = ProjectAdapter.this.ctx.getSharedPreferences(Constants.MEMORY, MODE_PRIVATE);
                memory.edit().putInt(Constants.PROJECT_ID, project.getId());
                ProjectAdapter.this.ctx.startActivity(new Intent(ProjectAdapter.this.ctx, InfoProjectActivity.class));
            }
        });

        return view;
    }
}
