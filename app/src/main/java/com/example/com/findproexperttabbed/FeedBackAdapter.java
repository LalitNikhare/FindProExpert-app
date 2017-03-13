package com.example.com.findproexperttabbed;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.com.findproexperttabbed.Notification.Professional.NotForProfAdapter;

public class FeedBackAdapter extends ArrayAdapter<String> {
    private String[] request;
    private String[] fname,lname;
    private Context context;

    public FeedBackAdapter( Context context,  String[] fname,String[] lname,String[] request) {
        super(context, R.layout.activity_feed_back_adapter, request);
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
                    .inflate(R.layout.activity_feed_back_adapter, parent, false);
            holder.request1 = (TextView) convertView.findViewById(R.id.feed_prof_name);
            holder.name1= (TextView) convertView.findViewById(R.id.feed_request);

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
