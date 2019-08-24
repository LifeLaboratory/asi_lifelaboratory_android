package ru.lifelaboratory.asi.adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.lifelaboratory.asi.R;
import ru.lifelaboratory.asi.entity.Category;
import ru.lifelaboratory.asi.entity.Project;

public class ProjectAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Project> projects;

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

        Project project = (Project) getItem(position);

        ImageView logo = (ImageView) view.findViewById(R.id.logo);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView author = (TextView) view.findViewById(R.id.author);
        TextView category = (TextView) view.findViewById(R.id.category);
        TextView description = (TextView) view.findViewById(R.id.description);
        TextView budget = (TextView) view.findViewById(R.id.budget);
        TextView rate = (TextView) view.findViewById(R.id.rate);
        FloatingActionButton info = (FloatingActionButton) view.findViewById(R.id.info);

        Picasso.with(this.ctx)
                .load(projects.get(position).getPhotoUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(logo);
        title.setText(projects.get(position).getTitle());
        //TODO сделать запрос на возвращение объекта пользователя
//        author.setText(projects.get(position).getId_author());
        description.setText(projects.get(position).getDescription());
        budget.setText(String.format("%f", projects.get(position).getBudget()));
        rate.setText(String.format("%f", projects.get(position).getRate()));

        ArrayList<Category> categories = (ArrayList<Category>) projects.get(position).getCategories();
        StringBuilder stringBuilder = new StringBuilder();
        for (int index = 0; index < categories.size(); index++) {
            if (index != 0) {
                stringBuilder.append("/");
            }
            stringBuilder.append(categories.get(index).getTitle());
        }
        category.setText(stringBuilder.toString());

        return view;
    }
}
