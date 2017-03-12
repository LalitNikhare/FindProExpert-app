package com.example.com.findproexperttabbed.Notification.Professional;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.com.findproexperttabbed.R;

public class NotForProfAdapter extends ArrayAdapter<String> {
    private String[] request;
    private String[] fname,lname;
    private Context context;

    public NotForProfAdapter( Context context,  String[] fname,String[] lname,String[] request) {
        super(context, R.layout.activity_not_for_prof_adapter, request);
        this.context=context;
        this.request=request;
        this.fname=fname;
        this.lname=lname;
    }
    private class ViewHolder {
        TextView request1, name1;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater
                    .inflate(R.layout.activity_not_for_prof_adapter, parent, false);
            holder.request1 = (TextView) convertView.findViewById(R.id.not_for_prof_request);
            holder.name1= (TextView) convertView.findViewById(R.id.not_for_prof_name);

            convertView.setTag(holder);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try{
            holder.request1.setText("" + request[position]);
            holder.name1.setText(""+fname[position]+" "+lname[position]);
        }catch(Exception e){
            e.printStackTrace();
        }

        return convertView;
    }
}
