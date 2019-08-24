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

import java.util.ArrayList;
import java.util.List;

import ru.lifelaboratory.asi.R;
import ru.lifelaboratory.asi.entity.CV;
import ru.lifelaboratory.asi.entity.Service;

public class ServicesAdapter extends BaseAdapter {

    List<Service> items = null;
    Context ctx = null;
    LayoutInflater lInflater = null;
    Service service = null;

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

    public ServicesAdapter(Context context, ArrayList<Service> items) {
        ctx = context;
        this.items = items;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = lInflater.inflate(R.layout.adapter_services, parent, false);
        }
        service = items.get(position);
        ((TextView) convertView.findViewById(R.id.service_title)).setText("Заголовок");
        ImageView photo = (ImageView)convertView.findViewById(R.id.service_img);
        Picasso.with(this.ctx)
                .load(service.getUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(photo);
        ((TextView) convertView.findViewById(R.id.service_description)).setText("Описание");

        return convertView;
    }
}