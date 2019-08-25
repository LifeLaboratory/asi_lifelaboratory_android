package ru.lifelaboratory.asi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.lifelaboratory.asi.LessonActivity;
import ru.lifelaboratory.asi.R;
import ru.lifelaboratory.asi.entity.Document;
import ru.lifelaboratory.asi.utils.Constants;

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
    public LessonAdapter(Context context, List<Document> items) {
        ctx = context;
        this.items = items;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = lInflater.inflate(R.layout.lesson_layout, parent, false);
        }
        final Document doc = items.get(position);
        Log.e(Constants.LOG_TAG, String.valueOf(position));
        ((TextView)convertView.findViewById(R.id.elementTitle)).setText(doc.getTitle());
        ImageView photo = (ImageView)convertView.findViewById(R.id.elementImg);
        Picasso.with(this.ctx)
                .load(doc.getPhoto())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(photo);
        ((TextView)convertView.findViewById(R.id.elementDesc)).setText(doc.getDescription());
        FloatingActionButton button = (FloatingActionButton)convertView.findViewById(R.id.moreInfo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDocInfo = new Intent(ctx, LessonActivity.class);
                Log.e(Constants.LOG_TAG, doc.getId().toString());
                Log.e(Constants.LOG_TAG, String.valueOf(position));
                toDocInfo.putExtra("DOC_ID", doc.getId());
                ctx.startActivity(toDocInfo);
            }
        });
        return convertView;
    }
}
