package ru.lifelaboratory.asi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.lifelaboratory.asi.R;
import ru.lifelaboratory.asi.entity.HistoryBudget;
import ru.lifelaboratory.asi.entity.Investor;

public class HistoryBudgetAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<HistoryBudget> historyBadgets;

    public HistoryBudgetAdapter(Context ctx, ArrayList<HistoryBudget> historyBadgets) {
        this.ctx = ctx;
        this.lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.historyBadgets = historyBadgets;
    }

    @Override
    public int getCount() {
        return historyBadgets.size();
    }

    @Override
    public Object getItem(int position) {
        return historyBadgets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.history_budget_layout, parent, false);
        }
        final HistoryBudget historyBudget = (HistoryBudget) getItem(position);
        TextView name = (TextView) view.findViewById(R.id.title);
        TextView budget = (TextView) view.findViewById(R.id.budget);

        name.setText(historyBudget.getTitle());
        budget.setText(String.format("Сумма: %.2f", historyBudget.getBudget()));

        return view;
    }
}
