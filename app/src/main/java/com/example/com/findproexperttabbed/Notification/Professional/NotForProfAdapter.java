package com.example.com.findproexperttabbed.Notification.Professional;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.findproexperttabbed.JSONNotification;
import com.example.com.findproexperttabbed.ProfileActivity;
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
        Button view;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater
                    .inflate(R.layout.activity_not_for_prof_adapter, parent, false);
            holder.request1 = (TextView) convertView.findViewById(R.id.not_for_prof_request);
            holder.name1= (TextView) convertView.findViewById(R.id.not_for_prof_name);
            holder.view=(Button)convertView.findViewById(R.id.not_for_prof_view);
            convertView.setTag(holder);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try{
            holder.request1.setText("" + request[position]);
            holder.name1.setText(""+fname[position]+" "+lname[position]);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String str_username= JSONNotification.cust_username[position];
                    Intent intent=new Intent(getContext(), ProfileActivity.class);
                    intent.putExtra("username",str_username);
                    intent.putExtra("view","view");
                    getContext().startActivity(intent);
                    Toast.makeText(getContext(),"Fetching Profile Contents",Toast.LENGTH_SHORT).show();
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }

        return convertView;
    }
}
