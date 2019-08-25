package ru.lifelaboratory.asi.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.lifelaboratory.asi.R;
import ru.lifelaboratory.asi.entity.Investor;

public class InvestitionAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Investor> investitions;

    public InvestitionAdapter(Context ctx, ArrayList<Investor> investitions) {
        this.ctx = ctx;
        this.lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.investitions = investitions;
    }

    @Override
    public int getCount() {
        return investitions.size();
    }

    @Override
    public Object getItem(int position) {
        return investitions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.investition_item, parent, false);
        }
        final Investor investor = (Investor) getItem(position);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView budget = (TextView) view.findViewById(R.id.budget);

        name.setText(investor.getName());
        budget.setText(String.format("Инвестиции: %.2f", investor.getBudget()));

        return view;
    }
}
