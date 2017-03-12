package com.example.com.findproexperttabbed.HomeScreen.Professional.ViewRequests;

import android.content.SharedPreferences;
import android.widget.ArrayAdapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.com.findproexperttabbed.Config;
import com.example.com.findproexperttabbed.JSONProfessional;
import com.example.com.findproexperttabbed.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by faltu on 13-Feb-17.
 */

public class ViewRequestAdapter extends ArrayAdapter<String> {
    private static final String SERIAL_NO = "srno";
    private static final String USERNAME = "username";
    private static final String ACCEPT_REQUEST_URL = "https://findproexpertcom.000webhostapp.com/accept_request.php";
    private Context context;
    private String[] request;
    private String[] cust_name;
    private String username;
//
    public ViewRequestAdapter(Context context,String[] request, String[] cust_name) {
        super(context, R.layout.activity_view_request_adapter,request);
        this.context = context;
        this.cust_name=cust_name;
        this.request=request;
    }
//    public ViewRequestAdapter(Context context,String[] request) {
//        super(context, R.layout.activity_view_request_adapter,request);
//        this.context = context;
//      //  this.cust_name=cust_name;
//        this.request=request;
//    }
    private class ViewHolder {
        TextView name1;
        TextView cust_name1;
        Button accept;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater
                    .inflate(R.layout.activity_view_request_adapter, parent, false);
            holder.name1 = (TextView) convertView.findViewById(R.id.prof_desc);
            holder.accept=(Button)convertView.findViewById(R.id.req_accept);
            holder.cust_name1 = (TextView) convertView.findViewById(R.id.cust_name);
            convertView.setTag(holder);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name1.setText("" + request[position]);
        holder.cust_name1.setText(""+cust_name[position]);

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchRequests(position);
                //Toast.makeText(getContext(),""+JSONProfessional.srno[position]+""+ JSONProfessional.view_request[position],Toast.LENGTH_SHORT).show();
            }
        });


        return convertView;
    }
    private void fetchRequests(final int position){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        username = sharedPreferences.getString(Config.USERNAME_SHARED_PREF,"Not Available");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ACCEPT_REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(),""+response,Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String ,String> params=new HashMap<String, String>();
                params.put(SERIAL_NO,String.valueOf(JSONProfessional.srno[position]));
                params.put(USERNAME,username);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
}

