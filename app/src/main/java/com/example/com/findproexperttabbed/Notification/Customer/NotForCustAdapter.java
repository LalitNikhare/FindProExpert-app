package com.example.com.findproexperttabbed.Notification.Customer;

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

public class NotForCustAdapter extends ArrayAdapter<String> {
    private String[] request;
    private int[] accept;
    private Context context;

    public NotForCustAdapter( Context context,  String[] request,int[] accept) {
        super(context, R.layout.activity_not_for_cust_adapter, request);
        this.context=context;
        this.request=request;
        this.accept=accept;
    }
    private class ViewHolder {
        TextView request1, accept1;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater
                    .inflate(R.layout.activity_not_for_cust_adapter, parent, false);
            holder.request1 = (TextView) convertView.findViewById(R.id.not_for_cust_request);
            holder.accept1= (TextView) convertView.findViewById(R.id.noti_for_accept_text);

            convertView.setTag(holder);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try{
            holder.request1.setText("" + request[position]);
            holder.accept1.setText("" + accept[position]);
        }catch(Exception e){
            e.printStackTrace();
        }

        return convertView;
    }
}
