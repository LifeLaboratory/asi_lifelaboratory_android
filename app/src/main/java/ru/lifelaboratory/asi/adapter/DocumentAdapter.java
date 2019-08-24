package ru.lifelaboratory.asi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.lifelaboratory.asi.R;
import ru.lifelaboratory.asi.entity.Document;

public class DocumentAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Document> documents;

    public DocumentAdapter(Context ctx, ArrayList<Document> documents) {
        this.ctx = ctx;
        this.documents = documents;
        this.lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return documents.size();
    }

    @Override
    public Object getItem(int position) {
        return documents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.info_project_layout, parent, false);
        }

        ImageView logo = (ImageView) view.findViewById(R.id.photo);
        TextView title = (TextView) view.findViewById(R.id.title);

        Document document = (Document) getItem(position);

        Picasso.with(this.ctx)
                .load(document.getPhoto())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(logo);
        title.setText(document.getTitle());

        return view;
    }
}
