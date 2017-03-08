package com.example.com.findproexperttabbed.Professional.ViewRequests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.com.findproexperttabbed.R;

import static com.example.com.findproexperttabbed.JSONProfessional.request;

/**
 * Created by faltu on 13-Feb-17.
 */

public class ViewRequestAdapter extends ArrayAdapter<String> {
    private Context context;
    private String[] request;


    public ViewRequestAdapter(Context context,String[] request) {
        super(context, R.layout.activity_view_request_adapter,request);
        this.context = context;
        this.request=request;
    }


    private class ViewHolder {
        TextView name1;
        Button accept,reject;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater
                    .inflate(R.layout.activity_view_request_adapter, parent, false);
            holder.name1 = (TextView) convertView.findViewById(R.id.prof_desc);
            holder.accept=(Button)convertView.findViewById(R.id.req_accept);
            holder.reject=(Button)convertView.findViewById(R.id.req_reject);
            convertView.setTag(holder);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
       // holder.img.setImageResource(image[position]);
        holder.name1.setText("" + request[position]);
        //holder.desc1.setText("" + desc[position]);
        //holder.worker1.setText("" + worker[position]);


        return convertView;
    }
}

