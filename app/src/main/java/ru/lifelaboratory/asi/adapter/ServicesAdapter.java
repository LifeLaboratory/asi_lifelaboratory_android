package ru.lifelaboratory.asi.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ru.lifelaboratory.asi.R;
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

    public ServicesAdapter(Context context, List<Service> items) {
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
        ((TextView) convertView.findViewById(R.id.service_title)).setText(service.getTitle());
        ((TextView) convertView.findViewById(R.id.service_description)).setText(service.getDescription());
        ((FloatingActionButton) convertView.findViewById(R.id.service_url)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(service.getUrl()));
                ctx.startActivity(i);
            }
        });

        return convertView;
    }
}