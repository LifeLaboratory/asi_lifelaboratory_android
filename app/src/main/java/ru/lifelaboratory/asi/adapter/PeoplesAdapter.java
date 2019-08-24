package ru.lifelaboratory.asi.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.lifelaboratory.asi.R;
import ru.lifelaboratory.asi.entity.CV;

public class PeoplesAdapter extends BaseAdapter {

    List<CV> items = null;
    Context ctx = null;
    LayoutInflater lInflater = null;
    CV cv = null;

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

    public PeoplesAdapter(Context context, List<CV> items) {
        ctx = context;
        this.items = items;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = lInflater.inflate(R.layout.adapter_peoples, parent, false);
        }
        cv = items.get(position);
        ((TextView) convertView.findViewById(R.id.cv_title)).setText("Заголовок");
        ImageView photo = (ImageView)convertView.findViewById(R.id.cv_img);
        Picasso.with(this.ctx)
                .load(cv.getUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(photo);
        ((TextView) convertView.findViewById(R.id.cv_description)).setText("Описание");

        FloatingActionButton button = (FloatingActionButton) convertView.findViewById(R.id.cv_url);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(cv.getUrl()));
                ctx.startActivity(i);
            }
        });
        return convertView;
    }
}