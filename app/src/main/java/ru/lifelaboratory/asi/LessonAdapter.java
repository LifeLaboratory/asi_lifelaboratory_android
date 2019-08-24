package ru.lifelaboratory.asi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.lifelaboratory.asi.entity.Document;

public class LessonAdapter extends BaseAdapter {
    List<Document> items = new ArrayList<>();
    Context ctx = null;
    LayoutInflater lInflater = null;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public LessonAdapter(Context context, ArrayList<Document> items) {
        ctx = context;
        this.items = items;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = lInflater.inflate(R.layout.lesson_layout, parent, false);
        }
        Document doc = items.get(position);
        ((TextView)convertView.findViewById(R.id.elementTitle)).setText(doc.getTitle());
        ImageView photo = (ImageView)convertView.findViewById(R.id.elementImg);
        Picasso.with(this.ctx)
                .load(doc.getLogo())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(photo);
        ((TextView)convertView.findViewById(R.id.elementDesc)).setText(doc.getDesc());
        return convertView;
    }
}
