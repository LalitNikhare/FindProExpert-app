package com.example.com.findproexperttabbed;

/**
 * Created by faltu on 09-Feb-17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by faltu on 03-Feb-17.
 */

public class ProfessionCustomList extends ArrayAdapter<String> {
    private String[] names;
    private String[] desc;
    private int[] worker;
    private Context context;

    public ProfessionCustomList(Context context, String[] names, String[] desc, int[] worker) {
        super(context, R.layout.profession_custom_list, names);
        this.context = context;
        this.desc = desc;
        this.names = names;
        this.worker = worker;
    }

    private class ViewHolder {
        TextView name1, desc1, worker1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater
                    .inflate(R.layout.profession_custom_list, parent, false);
            holder.name1 = (TextView) convertView.findViewById(R.id.profession_name);
            holder.desc1 = (TextView) convertView
                    .findViewById(R.id.profession_desc);
            holder.worker1 = (TextView) convertView
                    .findViewById(R.id.worker_count);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name1.setText("" + names[position]);
        holder.desc1.setText("" + desc[position]);
        holder.worker1.setText("" + worker[position]);


        return convertView;
    }
}
