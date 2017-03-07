package com.example.com.findproexperttabbed;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.findproexperttabbed.Professional.SeeRequests;

public class BecomeAProCustomAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] names;
    View view;

    public BecomeAProCustomAdapter(Context context, String[] names) {
        super(context, R.layout.activity_become_apro_custom_adapter, names);
        this.context = context;
        this.names = names;
    }
    private class ViewHolder {
        TextView name1;
        public CheckBox value;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater
                    .inflate(R.layout.activity_become_apro_custom_adapter, parent, false);
            holder.name1 = (TextView) convertView.findViewById(R.id.become_a_pro_text);
            holder.value = (CheckBox) convertView.findViewById(R.id.become_a_pro_checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }try{
            holder.name1.setText("" + names[position]);
        }
        catch(Exception e){

        }

        return convertView;
    }
}
