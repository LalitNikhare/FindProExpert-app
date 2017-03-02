package com.example.com.findproexperttabbed.Professional;

/**
 * Created by faltu on 09-Feb-17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.com.findproexperttabbed.R;

/**
 * Created by faltu on 03-Feb-17.
 */

public class SeeRequests extends ArrayAdapter<String> {
    private String[] names;
    private String[] desc;
    private int[] request;
    private Context context;

    public SeeRequests(Context context, String[] names, String[] desc, int[] request) {
        super(context, R.layout.customer_custom_list, names);
        this.context = context;
        this.desc = desc;
        this.names = names;
        this.request = request;
    }

    private class ViewHolder {
        TextView name1, desc1, request1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater
                    .inflate(R.layout.customer_custom_list, parent, false);
            holder.name1 = (TextView) convertView.findViewById(R.id.profession_name1);
            holder.desc1 = (TextView) convertView
                    .findViewById(R.id.profession_desc1);
            holder.request1 = (TextView) convertView
                    .findViewById(R.id.request_count);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name1.setText("" + names[position]);
        holder.desc1.setText("" + desc[position]);
        holder.request1.setText("" + request[position]);


        return convertView;
    }
}
