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

import ru.lifelaboratory.asi.InfoInvestorActivity;
import ru.lifelaboratory.asi.R;
import ru.lifelaboratory.asi.entity.User;
import ru.lifelaboratory.asi.utils.Constants;

import static android.content.Context.MODE_PRIVATE;

public class InvestorAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<User> investors;
    SharedPreferences memory;

    public InvestorAdapter(Context ctx, ArrayList<User> investors) {
        this.ctx = ctx;
        this.lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.investors = investors;
    }

    @Override
    public int getCount() {
        return investors.size();
    }

    @Override
    public Object getItem(int position) {
        return investors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.investor_item_layout, parent, false);
        }

        final User user = (User) getItem(position);

        ImageView logo = (ImageView) view.findViewById(R.id.logo);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView budget = (TextView) view.findViewById(R.id.budget);
        TextView description = (TextView) view.findViewById(R.id.description);
        TextView rate = (TextView) view.findViewById(R.id.rate);
        FloatingActionButton info = (FloatingActionButton) view.findViewById(R.id.info);

        budget.setText("Бюджет: " + user.getBudget());
        Picasso.with(this.ctx)
                .load(user.getPhotoUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(logo);
        title.setText(user.getName());
        description.setText(user.getDescription());
        rate.setText(String.format("%f", user.getRate()));

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memory = InvestorAdapter.this.ctx.getSharedPreferences(Constants.MEMORY, MODE_PRIVATE);
                // TODO: сменить USER_ID на INVESTOR_ID
                memory.edit().putInt(Constants.USER_ID, user.getId()).commit();
                InvestorAdapter.this.ctx.startActivity(new Intent(InvestorAdapter.this.ctx, InfoInvestorActivity.class));
            }
        });

        return view;
    }
}
